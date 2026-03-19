package ru.BirykovOleg.myfirstappbirykov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.BirykovOleg.myfirstappbirykov.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    // Теперь это список, а не один пост
    private var posts = listOf(
        Post(
            id = 1,
            author = "Футбольная афиша. Новости",
            content = "Добро пожаловать на наш специализированный ресурс, посвященный миру футбола! Если вам интересна самая свежая информация о матчах, игроках, клубах и событиях футбольного сообщества — вы попали по адресу.\n",
            published = "30 марта в 18:36",
            likedByMe = false,
            likes = 999,
            shares = 25,
            views = 400
        ),
        Post(
            id = 2,
            author = "Спартак",
            content = "В центре внимания находится судьба ведущего полузащитника Эсекьеля Барко..",
            published = "22 мая в 10:15",
            likedByMe = false,
            likes = 342,
            shares = 89,
            views = 500
        ),
        Post(
            id = 3,
            author = "Спорт-Экспресс",
            content = "Хуан Карлос Карседо официально представлен как главный тренер «Спартака». Испанский специалист подписал контракт до 2028 года",
            published = "5 января в 12:00",
            likedByMe = true,
            likes = 3450,
            shares = 890,
            views = 15600
        ),
        Post(
            id = 4,
            author = "Чемпионат",
            content = "«Спартак» объявил о переходе защитника Владислава Сауся из «Балтики». 22-летний футболист подписал контракт с красно-белыми",
            published = "26 января в 15:30",
            likedByMe = false,
            likes = 2100,
            shares = 450,
            views = 9800
        )
    )

    private val _data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = _data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun increaseViews(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        _data.value = posts
    }
}

