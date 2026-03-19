package ru.BirykovOleg.myfirstappbirykov.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.BirykovOleg.myfirstappbirykov.dto.Post
import ru.BirykovOleg.myfirstappbirykov.repository.PostRepository
import ru.BirykovOleg.myfirstappbirykov.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    init {
        println("ViewModel: created")
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel: cleared")
    }


    // Создаем экземпляр репозитория
    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    // Данные, доступные для наблюдения
    val data: LiveData<Post> = repository.get()

    // Методы для вызова из Activity
    fun like() = repository.like()
    fun share() = repository.share()
    fun increaseViews() = repository.increaseViews()
}
