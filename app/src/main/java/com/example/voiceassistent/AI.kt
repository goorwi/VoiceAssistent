package com.example.voiceassistent

import java.util.Locale

class AI {
    val answers = mapOf<String, String>(
        "Привет" to "Приветик",
        "Чем занимаешься?" to "Отвечаю на вопросы",
        "Что делаешь?" to "Отвечаю на вопросы",
        "Как дела?" to "Неплохо",
        "Ответишь на вопросик?" to "Конечно",
        "Любишь гранатовый сок?" to "Хоть я и робот, но люблю"
    )

    fun getAnswer(text: String): String {
        var result : String? = null
        for ((x, y) in answers) {
            if (text.contains(x, ignoreCase = true))
                result = y
        }
        if (result == null) return "Вопрос понял. Думаю..."
        else return result
    }
}