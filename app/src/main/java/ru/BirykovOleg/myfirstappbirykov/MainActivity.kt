package ru.BirykovOleg.myfirstappbirykov
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.room.util.copy
import androidx.room3.util.copy
import ru.BirykovOleg.myfirstappbirykov.databinding.ActivityMainBinding
import ru.BirykovOleg.myfirstappbirykov.dto.Post
import ru.BirykovOleg.myfirstappbirykov.util.FormatUtils.formatCount
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Создаем экземпляр Binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 2. Устанавливаем корневой View как content view
        setContentView(binding.root)

        // 3. Создаем тестовые данные
        post = Post(
            id = 1,
            author = "Футбольная афиша. Новости",
            content = "Добро пожаловать на наш специализированный ресурс, посвященный миру футбола! Если вам интересна самая свежая информация о матчах, игроках, клубах и событиях футбольного сообщества — вы попали по адресу.",
            published = "30 марта в 13:23",
            likedByMe = false,
            likes = 1000000,
            shares = 25,
            views = 5700
        )

        // 4. Отображаем данные на экране
        bindPost(post)

        // 5. Обработка кликов
        setupClickListeners()
    }

    private fun bindPost(post: Post) {
        // Используем View Binding для доступа к View
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            // Устанавливаем текст для счетчиков с форматированием
            likeCount.text = formatCount(post.likes)
            shareCount.text = formatCount(post.shares)
            viewsCount.text = formatCount(post.views)

            // Устанавливаем правильную иконку лайка в зависимости от состояния
            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_like_filled)
            } else {
                like.setImageResource(R.drawable.ic_like_border)
            }

            // Пример с ссылкой (заполняем, если есть)
            linkTitle.text = "Самые свежие новости"
            linkUrl.text = "football_poster.ru"
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            // Обработка лайка
            like.setOnClickListener {
                // Меняем состояние
                post = post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )

                // Обновляем отображение
                bindPost(post)
                println("CLICK: Лайк")
                // Показываем подсказку (для наглядности)
                Toast.makeText(this@MainActivity,
                    if (post.likedByMe) "Лайк поставлен" else "Лайк убран",
                    Toast.LENGTH_SHORT).show()
            }

            // Обработка репоста
            share.setOnClickListener {

                // Увеличиваем счетчик репостов на 1
                post = post.copy(
                    shares = post.shares + 1
                )

                // Обновляем отображение
                bindPost(post)
                println("CLICK: Репост +1")
                Toast.makeText(this@MainActivity, "Репост +1", Toast.LENGTH_SHORT).show()
            }

            // Обработка меню (просто показать сообщение)
            menu.setOnClickListener {
                println("CLICK: Меню поста")
                Toast.makeText(this@MainActivity, "Меню поста", Toast.LENGTH_SHORT).show()
            }

            // Обработка аватарки
            avatar.setOnClickListener {
                println("CLICK: Профиль автора")
                Toast.makeText(this@MainActivity, "Профиль автора", Toast.LENGTH_SHORT).show()
            }

            // Обработка всего корневого layout (для исследования)
            root.setOnClickListener {
                println("CLICK: Клик по фону")
                Toast.makeText(this@MainActivity, "Клик по фону", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    /
//    Форматирует число в удобочитаемый вид:
//    999 -> 999
//    1000 -> 1K
//    1100 -> 1.1K
//    10000 -> 10K
//    11000 -> 11K (сотни не отображаются после 10K)
//    1000000 -> 1M
//    1300000 -> 1.3M
//    /

}
