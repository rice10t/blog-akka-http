package session

import com.softwaremill.session.{BasicSessionEncoder, SessionConfig, SessionManager, SessionSerializer}

class AppSessionManager {
  val sessionEncoder = new BasicSessionEncoder[Long]()(SessionSerializer.longToStringSessionSerializer)
  val sessionConfig =
    SessionConfig.default("some_very_long_secret_and_random_string_some_very_long_secret_and_random_string")
  val sessionManager = new SessionManager[Long](sessionConfig)(sessionEncoder)
}
