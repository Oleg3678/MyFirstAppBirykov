package ru.BirykovOleg.myfirstappbirykov.adapter

import ru.BirykovOleg.myfirstappbirykov.dto.Post

interface OnPostInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onAvatarClick(post: Post) {}

    fun onPostClick(post: Post) {}
}
