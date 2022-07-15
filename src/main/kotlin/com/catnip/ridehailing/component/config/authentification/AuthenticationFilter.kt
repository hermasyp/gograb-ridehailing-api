package com.catnip.ridehailing.component.config.authentification

import com.catnip.ridehailing.component.config.exception.AppException
import com.catnip.ridehailing.utils.constants.CommonConstants
import com.catnip.ridehailing.utils.constants.SystemEnv
import com.catnip.ridehailing.wrapper.BaseResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            if (JwtConfig.isPermit(request)) {
                filterChain.doFilter(request, response)
            } else {
                val claims = validate(request)
                if (claims[CommonConstants.CLAIMS] != null) {
                    setupAuthentication(claims) {
                        filterChain.doFilter(request, response)
                    }
                } else {
                    SecurityContextHolder.clearContext()
                    throw AppException("Token Required")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val errorResponse = BaseResponse<Nothing>()
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"

            when (e) {
                is UnsupportedJwtException -> {
                    errorResponse.message = "Unsupported Jwt !"
                    val responseString = ObjectMapper()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(errorResponse)

                    response.writer.println(responseString)
                }
                else -> {
                    errorResponse.message = e.message ?: "Token Invalid"
                    val responseString = ObjectMapper()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(errorResponse)
                    response.writer.println(responseString)
                }
            }
        }

    }

    private fun validate(request: HttpServletRequest): Claims {
        val jwtToken = request.getHeader("Authorization")
        return Jwts.parserBuilder()
            .setSigningKey(SystemEnv.SECRET.toByteArray())
            .build()
            .parseClaimsJws(jwtToken)
            .body
    }

    private fun setupAuthentication(claims: Claims, doOnNext: () -> Unit) {
        val authorities = claims[CommonConstants.CLAIMS] as List<*>
        val authStream = authorities.stream().map { SimpleGrantedAuthority(it.toString()) }
            .collect(Collectors.toList())
        val auth = UsernamePasswordAuthenticationToken(claims.subject, null, authStream)
        SecurityContextHolder.getContext().authentication = auth
        doOnNext.invoke()
    }
}