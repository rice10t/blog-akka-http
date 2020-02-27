package session

import com.softwaremill.session.SessionOptions._

class SessionDirectives(appSessionManager: AppSessionManager) {
  def setSession(value: Long) = {
    com.softwaremill.session.SessionDirectives.setSession(oneOff(appSessionManager.sessionManager), usingCookies, value)
  }

  def requiredSession = {
    com.softwaremill.session.SessionDirectives.requiredSession(oneOff(appSessionManager.sessionManager), usingCookies)
  }
}
