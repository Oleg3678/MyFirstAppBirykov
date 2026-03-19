package ru.BirykovOleg.myfirstappbirykov
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import ru.BirykovOleg.myfirstappbirykov.activity.EditPostContract
import ru.BirykovOleg.myfirstappbirykov.adapter.OnPostInteractionListener
import ru.BirykovOleg.myfirstappbirykov.adapter.PostsAdapter
import ru.BirykovOleg.myfirstappbirykov.databinding.ActivityMainBinding
import ru.BirykovOleg.myfirstappbirykov.dto.Post
import ru.BirykovOleg.myfirstappbirykov.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    // ID поста, который редактируется (0 = новый пост)
    private var editingPostId: Long = 0L
    private val editPostLauncher = registerForActivityResult(EditPostContract()) { result ->
        if (!result.isNullOrBlank()) {
            // Получен текст отредактированного/нового поста
            viewModel.changeContent(result)
            viewModel.save()
        }
    }


    private val interactionListener = object : OnPostInteractionListener {
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {

            viewModel.shareById(post.id)
            Toast.makeText(this@MainActivity, "Репост +1", Toast.LENGTH_SHORT).show()
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val chooserIntent =
                Intent.createChooser(shareIntent, getString(R.string.share_post_via))
            startActivity(chooserIntent)
            viewModel.shareById(post.id)

        }

        override fun onEdit(post: Post) {
            editPostLauncher.launch(post.content)
            // Сохраняем ID редактируемого поста
            editingPostId = post.id
            // Устанавливаем текст в поле ввода
            binding.content.setText(post.content)
            binding.content.setSelection(binding.content.text.length)
            // Переводим фокус и показываем клавиатуру
            binding.content.requestFocus()
            showKeyboard(binding.content)
            // Показываем панель отмены
            binding.cancelGroup.visibility = View.VISIBLE
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
            Toast.makeText(this@MainActivity, "Пост удален", Toast.LENGTH_SHORT).show()
        }

        override fun onAvatarClick(post: Post) {
            Toast.makeText(this@MainActivity, "Профиль: ${post.author}", Toast.LENGTH_SHORT).show()
            viewModel.increaseViews(post.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка адаптера
        val adapter = PostsAdapter(interactionListener)
        binding.list.adapter = adapter

        // Наблюдение за списком постов
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        // Увеличиваем счетчик репостов

        // Отслеживание изменений текста от пользователя
        binding.content.addTextChangedListener { text ->
            // Обновляем ViewModel при изменении текста пользователем
            viewModel.changeContent(text.toString())
        }

        // Кнопка сохранения
        binding.save.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isBlank()) {
                Toast.makeText(this, "Введите текст поста", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Если редактируем существующий пост
            if (editingPostId != 0L) {
                // Получаем текущий пост из ViewModel, обновляем его контент и сохраняем
                viewModel.saveEditedPost(editingPostId, text)
                editingPostId = 0L
            } else {
                // Создаем новый пост
                viewModel.changeContent(text)
                viewModel.save()
            }

            // Очищаем поле ввода
            binding.content.text.clear()
            // Скрываем панель отмены
            binding.cancelGroup.visibility = View.GONE
            // Скрываем клавиатуру
            hideKeyboard(binding.content)
        }
        binding.fab.setOnClickListener {
            // Запускаем создание нового поста
            editPostLauncher.launch(null)  // null означает создание нового
        }

        // Кнопка отмены редактирования
        binding.cancel.setOnClickListener {
            // Очищаем ID редактируемого поста
            editingPostId = 0L
            // Очищаем поле ввода
            binding.content.text.clear()
            // Скрываем панель отмены
            binding.cancelGroup.visibility = View.GONE
            // Скрываем клавиатуру
            hideKeyboard(binding.content)
            // Отменяем редактирование в ViewModel
            viewModel.cancelEdit()
        }
    }

    private fun hideKeyboard(view: View) {
        val imm =
            getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        val imm =
            getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.showSoftInput(view, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
    }


}







