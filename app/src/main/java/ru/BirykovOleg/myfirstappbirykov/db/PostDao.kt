package ru.BirykovOleg.myfirstappbirykov.db

import ru.BirykovOleg.myfirstappbirykov.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun getById(id: Long): Post?
    fun insert(post: Post): Post
    fun update(post: Post): Post
    fun delete(id: Long)
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun increaseViews(id: Long)
}
