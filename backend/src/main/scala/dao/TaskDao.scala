package dao

import model.Task

class TaskDao(ctx: MyContext) {
  import ctx._

  def find(id: Long): Option[Task] =
    run {
      quote {
        query[Task]
          .filter(t => t.id == lift(id))
      }
    }.headOption

  def findAll(): Seq[Task] = run {
    quote {
      query[Task]
    }
  }

  def create(title: String): Long = run {
    quote {
      query[Task]
        .insert(lift(Task(-1, title, false)))
        .returning(_.id)
    }
  }

  def delete(id: Long): Long = run {
    quote {
      query[Task]
        .filter(_.id == lift(id))
        .delete
    }
  }

  def done(id: Long): Unit = run {
    quote {
      query[Task]
        .filter(_.id == lift(id))
        .update(t => t.done -> true)
    }
  }

  def undone(id: Long): Unit = run {
    quote {
      query[Task]
        .filter(_.id == lift(id))
        .update(t => t.done -> false)
    }
  }
}
