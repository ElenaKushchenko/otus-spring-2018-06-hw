package ru.otus.spring.kushchenko.hw6.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw6.entity.Comment
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class CommentRepositoryImpl : CommentRepository {
    private val log = LoggerFactory.getLogger(CommentRepositoryImpl::class.java)

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun getAll(): List<Comment> {
        log.debug("Getting all comments")

        val query = "SELECT c FROM Comment c"
        return entityManager.createQuery(query, Comment::class.java)
            .resultList
    }

    override fun get(id: Int): Comment {
        log.debug("Getting comment with id = $id")

        return entityManager.find(Comment::class.java, id)
    }

    @Transactional
    override fun create(comment: Comment): Comment {
        log.debug("Inserting a new comment: $comment")

        entityManager.persist(comment)
        entityManager.flush()
        entityManager.refresh(comment)

        return comment
    }

    @Transactional
    override fun update(id: Int, comment: Comment): Comment {
        log.debug("Updating comment with id = ${comment.id}")

        val toUpdate = entityManager.find(Comment::class.java, id)
        toUpdate?.let {
            it.text = comment.text

            entityManager.flush()
            entityManager.refresh(it)
        }

        return toUpdate
    }

    @Transactional
    override fun delete(id: Int) {
        log.debug("Deleting comment with id = $id")

        val toDelete = entityManager.find(Comment::class.java, id)
        toDelete?.let { entityManager.remove(it) }
    }
}