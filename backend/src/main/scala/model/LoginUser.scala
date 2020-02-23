package model

// TODO password must be hashed
case class LoginUser(id: Long, username: String, password: String)
