package ru.BirykovOleg.myfirstappbirykov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.BirykovOleg.myfirstappbirykov.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    // Исходные данные
    private var post = Post(
        id = 1,
        author = "Футбольная афиша. Новости",
        content = "Добро пожаловать на наш специализированный ресурс, посвященный миру футбола! Если вам интересна самая свежая информация о матчах, игроках, клубах и событиях футбольного сообщества — вы попали по адресу.",
        published = "30 марта в 13:23",
        likedByMe = false,
        likes = 999,
        shares = 25,
        views = 5700
    )

    // MutableLiveData, который можно изменять
    private val _data = MutableLiveData(post)

    // Внешний доступ только для чтения (LiveData, а не MutableLiveData)
    override fun get(): LiveData<Post> = _data

    override fun like() {
        // Меняем состояние лайка на противоположное
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
        )
        // Оповещаем подписчиков об изменении
        _data.value = post
    }

    override fun share() {
        post = post.copy(
            shares = post.shares + 1
        )
        _data.value = post
    }

    override fun increaseViews() {
        // Можно будет реализовать позже
        post = post.copy(
            views = post.views + 1
        )
        _data.value = post
    }
}
