package module

import com.softwaremill.macwire.wire
import dao.{GithubUserDao, MyContext, TaskDao, UserDao}

class TestModule extends MainModule {
  // daos
  override lazy val quillContext = wire[MyContext]
  override lazy val githubUserDao = wire[GithubUserDao]
  override lazy val taskDao = wire[TaskDao]
  override lazy val UserDao = wire[UserDao]
}
