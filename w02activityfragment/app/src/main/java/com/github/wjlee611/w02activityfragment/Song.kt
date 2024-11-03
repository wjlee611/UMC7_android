package com.github.wjlee611.w02activityfragment

data class Song(
    val title: String = "",
    val singer: String = "",
    val second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false
)
