package ru.BirykovOleg.myfirstappbirykov.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.BirykovOleg.myfirstappbirykov.dto.Post
import ru.BirykovOleg.myfirstappbirykov.repository.PostRepository
import ru.BirykovOleg.myfirstappbirykov.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data: LiveData<List<Post>> = repository.getAll()

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun increaseViews(id: Long) = repository.increaseViews(id)


}
