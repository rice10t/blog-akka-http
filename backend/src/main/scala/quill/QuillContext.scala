package quill

import io.getquill._

class MyContext extends PostgresJdbcContext(SnakeCase, "ctx")
