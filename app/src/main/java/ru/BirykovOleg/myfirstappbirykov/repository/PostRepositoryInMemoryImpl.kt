package ru.BirykovOleg.myfirstappbirykov.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.BirykovOleg.myfirstappbirykov.dto.Post
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class PostRepositoryInMemoryImpl : PostRepository {

    // Счетчик для генерации ID
    private var nextId = 5L

    // Текущий пользователь (для демонстрации)
    private val currentUserId = 1L
    private val currentUserName = "Я"

    private var posts = listOf(
        Post(
            id = 1,
            author = "Sports.ru",
            authorId = 2,
            content = "Добро пожаловать на наш специализированный ресурс, посвященный миру футбола! Если вам интересна самая свежая информация о матчах, игроках, клубах и событиях футбольного сообщества — вы попали по адресу.\n",
            published = "30 марта в 18:36",
            likedByMe = false,
            likes = 777,
            shares = 25,
            views = 5700,
            video = "https://yandex.ru/video/preview/8369652231282710364",


        ),
        Post(
            id = 2,
            author = "Euro-football.ru",
            authorId = 3,
            content = "В центре внимания находится судьба ведущего полузащитника Эсекьеля Барко.Действующий контракт Барко рассчитан до лета 2027 года . Однако переговоры о его продлении уже ведутся.\\n\" +\n" +
                    "            \"\\n\" +\n" +
                    "            \"Позиция клуба: Спортивный директор «Спартака» Фрэнсис Кахигао неоднократно подчеркивал, что клуб хочет сохранить Барко на ближайшие годы, считая его важным игроком .\\n\" +\n" +
                    "            \"\\n\" +\n" +
                    "            \"Позиция игрока: Главная загвоздка — в зарплате. Сообщается, что Барко хочет контракт, сопоставимый по условиям с соглашением Жедсона Фернандеша (лидера команды), и рассчитывает зарабатывать не менее €3 млн в год .\n",
            published = "вчера в 18:45",
            likedByMe = false,
            likes = 342,
            shares = 89,
            views = 2300,
            video = "https://yandex.ru/video/preview/8369652231282710364"
        ),
        Post(
            id = 3,
            author = "Спорт-Экспресс",
            authorId = 4,
            content = "Хуан Карлос Карседо официально представлен как главный тренер «Спартака». Испанский специалист подписал контракт до 2028 года\",\n" +
                    "    published = \"5 января в 12:00\n",
            published = "3 дня назад",
            likedByMe = false,
            likes = 1250,
            shares = 420,
            views = 8900,
            video = null
        ),
        Post(
            id = 4,
            author = "Чемпионат",
            authorId = 5,
            content = "\"«Спартак» объявил о переходе защитника Владислава Сауся из «Балтики». 22-летний футболист подписал контракт с красно-белыми На Владислава Сауся претендовали практически все топ-клубы России. Особенно активно его хотел приобрести ЦСКА — армейцы вели переговоры и рассматривали футболиста как усиление правого фланга .\\n\" +\n" +
                    "            \"\\n\" +\n" +
                    "            \"Спортивный директор «Балтики» Армен Маргарян подтвердил:\\n\" +\n" +
                    "            \"\\n\" +\n" +
                    "            \"«На Владислава претендовали практически все топ-клубы России, а также были варианты из Европы. В итоге футболист сделал выбор в пользу «Спартака» .\\n\" +\n" +
                    "            \"\\n\" +\n" +
                    "            \"Интересно, что сам игрок предпочитал вариант с ЦСКА, однако переговоры не увенчались успехом. Также Саусь был не против доиграть сезон в Калининграде, но «Спартак» сумел убедить его перейти именно сейчас \n",
            published = "вчера",
            likedByMe = false,
            likes = 510,
            shares = 11,
            views = 129,
            video = null
        ),

        Post(
            id = 5,
            author = "Bombardir.ru",
            authorId = 6,
            content = "Анонс матча 25-го тура РПЛ: «Спартак» — «Зенит». Встреча состоится 2 апреля в 19:30 на стадионе «Открытие Банк Арена». Ожидается аншлаг",
            published = "сегодня в 11:15",
            likedByMe = false,
            likes = 5678,
            shares = 1234,
            views = 45000,
                    video = "https://yandex.ru/video/preview/16363886216117016915"

        ),
        Post(
            id = 6,
            author = "ФНК (Футбол на Куличках",
            authorId = 7,
            content = "Поздравляем Александра Соболева с днем рождения! Желаем новых спортивных достижений, крепкого здоровья и ярких побед в составе красно-белых! 7 марта нападающему «Зенита» и сборной России Александру Соболеву исполнилось 29 лет .\\n\" +\n" +
                    "            \"\\n\" +\n" +
                    "            \"Желаем новых спортивных достижений, крепкого здоровья, ярких голов и громких побед! Пусть каждый матч приносит радость, а характер, который делает игру такой эмоциональной, помогает добиваться самых высоких целей.\\n\" +\n" +
                    "            \"\\n\" +\n" +
                    "            \"Кстати, о характере. Сам Соболев недавно признался: «Надо быть самим собой: где-то с публикой позаигрывать, где-то понаглее быть на поле. В общем, я решил быть таким же, каким был в \\\"Спартаке\\\", когда меня не любили и ненавидели — плохим Сашей» . И этот настрой приносит результат: после возобновления сезона форвард забил 3 гола в 5 матчах, причем два из них стали победными — в ворота «Балтики» в Кубке и, что особенно принципиально, в ворота бывшего клуба — московского «Спартака» (2:0) .\",\n",
            published = "сегодня в 12:15",
            likedByMe = false,
            likes = 100,
            shares = 11000,
            views = 1232,
            video = null
        ),
        Post(
            id = 7,
            author = "Матч ТВ",
            authorId = 8,
            content ="Экс-тренер «Спартака» Гильермо Абаскаль возглавил испанскую «Гранаду». Контракт подписан до конца сезона с возможностью продления.,\n"
                    ,
            published = "вчера в 12:15",
            likedByMe = false,
            likes = 5678,
            shares = 1234,
            views = 45000,
            video = null
        ),
        Post(
            id = 8,
            author = "RT на русском. Спорт",
            authorId = 9,
            content = "Российская Премьер-Лига утвердила календарь оставшихся матчей сезона. «Спартак» проведет 4 домашние встречи до конца чемпионата.\",\n"
                   ,
            published = "четверг в 12:15",
            likedByMe = false,
            likes = 5678,
            shares = 1234,
            views = 45000,
            video = null

        ),
        Post(
            id = 9,
            author = "Football.kulichki.net",
            authorId = 10,
            content = "Лучшие моменты матча «Спартак» — «Динамо» (3:2). Голы Промеса, Игнатова и Соболева принесли красно-белым волевую победу в дерби!\",\n"
                   ,
            published = "позавчера в 10:00",
            likedByMe = false,
            likes = 518,
            shares = 125,
            views = 45,
            video = "https://yandex.ru/video/preview/8369652231282710364"
        ),
        Post(
            id = 10,
            author = "Goal.com Россия",

            authorId = 11,
            content = "Билеты на матч со «Спартаком» из Кисловодска уже в продаже. Приходи поддержать команду на стадионе",
            published = "позавчера в 10:00",
            likedByMe = false,
            likes = 5128,
            shares = 1225,
            views = 451,
            video = null
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

    override fun save(post: Post): Post {
        if (post.id == 0L) {
            // Создание нового поста
            val newPost = post.copy(
                id = nextId++,
                author = currentUserName,
                authorId = currentUserId,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0
            )
            posts = listOf(newPost) + posts
        } else {
            // Обновление существующего поста
            posts = posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    // Сохраняем автора, дату и счетчики, обновляем только контент
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        _data.value = posts
        return TODO("Provide the return value")
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        _data.value = posts
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }
}





