package com.example.voiceassistent

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.LongAccumulator

class AI {
    enum class timeAnswer {
        day,
        hour,
        dayOfWeek,
        timeToDay
    }

    val answers = mapOf(
        "Привет" to "Приветик",
        "Чем занимаешься" to "Отвечаю на вопросы",
        "Что делаешь" to "Отвечаю на вопросы",
        "Как дела" to "Неплохо",
        "Ответишь на вопросик" to "Конечно",
        "Любишь гранатовый сок" to "Хоть я и робот, но люблю",
        "Какой сегодня день" to getTimeAnswer(timeAnswer.day, "null"),
        "Который час" to getTimeAnswer(timeAnswer.hour, "null"),
        "Какой день недели" to getTimeAnswer(timeAnswer.dayOfWeek, "null"),
        "Йоу" to "Ты шизик? Окстись!!",
        "Хи" to "Иди поспи)))"
    )

    fun getAnswer(text: String): String {
        var result: String? = null
        for ((x, y) in answers) {
            if (text.contains("сколько дней до", ignoreCase = true)) {
                result = getTimeAnswer(timeAnswer.timeToDay, text.substring(text.lastIndexOf(" ") + 1))
            }
            else if (text.contains(x, ignoreCase = true))
                result = y
        }
        if (result == null) return "Вопрос понял. Думаю..."
        else return result
    }

    private fun getTimeAnswer(type: Enum<timeAnswer>, date: String): String {
        when (type) {
            timeAnswer.day -> {
                return android.text.format.DateFormat.format("dd.MM.yyyy", Calendar.getInstance())
                    .toString()
            }

            timeAnswer.hour -> {
                return android.text.format.DateFormat.format("HH:mm", Calendar.getInstance()).toString()
            }

            timeAnswer.dayOfWeek -> {
                return android.text.format.DateFormat.format("EEEE", Calendar.getInstance()).toString()
            }

            timeAnswer.timeToDay -> {
                    val targetDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(date)
                    if (targetDate != null) {
                        val currentDate = Date()
                        val differenceInMillis = targetDate!!.time - currentDate.time
                        val daysDifference = differenceInMillis / (24 * 60 * 60 * 1000)

                        return if (daysDifference >= 0) {
                            "До ${
                                SimpleDateFormat(
                                    "dd.MM.yyyy",
                                    Locale.getDefault()
                                ).format(targetDate!!)
                            } осталось $daysDifference дней"
                        } else {
                            "Уже прошло"
                        }
                    } else {
                        return "Вы не указали конечную дату. Пожалуйста, уточните дату в формате dd.MM.yyyy."
                    }
            }

            else -> {
                return "Вопрос понял. Думаю..."
            }
        }
    }
}