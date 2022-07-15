package com.catnip.ridehailing.utils.constants

object CommonConstants{
  const val CLAIMS = "auth"
}
object ApiConstants {
  const val VERSION = "v1"
  const val BASE_URL_API = "/api/$VERSION"
}
object SystemEnv{
  val SECRET: String = System.getenv(EnvConstants.ENV_SECRET)
  val DATABASE: String = System.getenv(EnvConstants.ENV_DATABASE_URL)
}
object EnvConstants{
  const val ENV_DATABASE_URL = "DATABASE_URL"
  const val ENV_SECRET = "SECRET"
}
object RoleConstants{
  const val ROLE_USER = "role_user"
  const val ROLE_DRIVER = "role_driver"
}