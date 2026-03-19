package ru.BirykovOleg.myfirstappbirykov
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.BirykovOleg.myfirstappbirykov.databinding.ActivityMainBinding
import ru.BirykovOleg.myfirstappbirykov.dto.Post
import ru.BirykovOleg.myfirstappbirykov.viewmodel.PostViewModel
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    // Делегирование создания ViewModel
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Activity: onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Подписываемся на изменения данных
        viewModel.data.observe(this) { post ->
            // Этот код будет выполняться каждый раз, когда данные изменяются
            bindPost(post)
        }

        setupClickListeners()
    }
    override fun onStart() {
        super.onStart()
        println("Activity: onStart")
    }

    override fun onResume() {
        super.onResume()
        println("Activity: onResume")
    }

    override fun onPause() {
        super.onPause()
        println("Activity: onPause")
    }

    override fun onStop() {
        super.onStop()
        println("Activity: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Activity: onDestroy")
    }


    private fun bindPost(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            // Форматируем и отображаем счетчики
            likeCount.text = formatCount(post.likes)
            shareCount.text = formatCount(post.shares)
            viewsCount.text = formatCount(post.views)

            // Устанавливаем иконку лайка в зависимости от состояния
            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_like_filled)
            } else {
                like.setImageResource(R.drawable.ic_like_border)
            }

            // Пример с ссылкой

            linkTitle.text = "Футбольная афиша. Новости"
            linkUrl.text = "football_poster.ru"
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            // Обработка лайка - вызываем метод ViewModel
            like.setOnClickListener {
                println("CLICK: Клик по лайку")
                viewModel.like()
                Toast.makeText(this@MainActivity, "Лайк", Toast.LENGTH_SHORT).show()
            }

            // Обработка репоста - вызываем метод ViewModel
            share.setOnClickListener {
                println("CLICK: Репост")
                viewModel.share()
                Toast.makeText(this@MainActivity, "Репост +1", Toast.LENGTH_SHORT).show()
            }

            menu.setOnClickListener {
                println("CLICK: Меню поста")
                Toast.makeText(this@MainActivity, "Меню поста", Toast.LENGTH_SHORT).show()
            }

            avatar.setOnClickListener {
                println("CLICK: Профиль аватара")
                Toast.makeText(this@MainActivity, "Профиль автора", Toast.LENGTH_SHORT).show()
                // Увеличиваем просмотры при клике на аватар (для примера)
                viewModel.increaseViews()
            }

            // Для исследования поведения
            root.setOnClickListener {
                println("CLICK: Клик по фону")
                Toast.makeText(this@MainActivity, "Клик по фону", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun formatCount(count: Int): String {
        return when {
            count >= 1_000_000 -> {
                val millions = count / 1_000_000.0
                if (millions % 1.0 == 0.0) {
                    "${millions.toInt()}M"
                } else {
                    DecimalFormat(".").format(millions) + "M"
                }
            }
            count >= 10_000 -> {
                "${count / 1000}K"
            }
            count >= 1_000 -> {
                val thousands = count / 1000.0
                if (thousands % 1.0 == 0.0) {
                    "${thousands.toInt()}K"
                } else {
                    DecimalFormat(".").format(thousands) + "K"
                }
            }
            else -> count.toString()
        }

    }
}

