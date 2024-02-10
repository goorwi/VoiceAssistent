package com.example.voiceassistent.holidays

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.regex.Matcher
import java.util.regex.Pattern

class ParsingHtmlService {
    private var URL = "http:/mirkosmosa.ru/holiday/2024"

    @RequiresApi(Build.VERSION_CODES.O)
    fun getHoliday(date: String?): String {
        var holidays: String = ""
        val days: ArrayList<String> = getDate(date)
        for (i in 0..<days.size) {
            if (days[i][0] == '0') days[i] = days[i].substring(1)
        }

        val document: Document = Jsoup.connect(URL).get()

        val body: Element = document.body()
        val daysOfMonth: Elements = body.select("#holiday_calend > div > div > div")
        for (dayInMonth in daysOfMonth) {
            for (day in days) {
                if (dayInMonth.getElementsByTag("span")[0].text() == day) {
                    val holiday = dayInMonth.getElementsByTag("a")
                    holidays = holidays.plus("$day : ")
                    for (h in holiday) {
                        holidays = holidays.plus(h.text() + ", ")
                    }
                    holidays = holidays.dropLast(2)
                    holidays = holidays.plus("\n")
                }
            }

        }
        return if (holidays == "")
            "хз"
        else holidays.dropLast(1)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(date: String?): ArrayList<String> {
        val day: ArrayList<String> = ArrayList()
        var prevDate = date
        var pattern: Pattern = Pattern.compile("какой праздник (\\p{L}+)", Pattern.CASE_INSENSITIVE)
        var matcher: Matcher = pattern.matcher(date!!)
        var dateEx = date
        while (matcher.find()) {
            day.add(matcher.group(1)!!)
            dateEx = dateEx?.replace(day.last() + " и ", "")
            if (dateEx == prevDate) break
            matcher = pattern.matcher(dateEx!!)
            prevDate = dateEx
        }
        pattern = Pattern.compile("(\\p{N}+) (\\p{L}+) (\\p{N}+)", Pattern.CASE_INSENSITIVE)
        matcher = pattern.matcher(date)
        while (matcher.find()) {
            day.add(matcher.group())
            dateEx = dateEx?.replace(day.last(), "")
            if (dateEx == prevDate) break
            matcher = pattern.matcher(dateEx!!)
            prevDate = dateEx
        }
        pattern = Pattern.compile("(\\p{N}+.\\p{N}+.\\p{N}+)", Pattern.CASE_INSENSITIVE)
        matcher = pattern.matcher(date)
        while (matcher.find()) {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val result = formatter.parse(matcher.group(1))
            day.add(DateTimeFormatter.ofPattern("dd MMMM yyyy").format(result))

            dateEx = dateEx?.replace(matcher.group(1)!!, "")
            if (dateEx == prevDate) break
            matcher = pattern.matcher(dateEx!!)
            prevDate = dateEx
        }

        if (day.contains("завтра")) {
            day.remove("завтра")
            var currentDate = LocalDate.now()
            currentDate = currentDate.plusDays(1)
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            day.add(currentDate.format(formatter))
        }

        if (day.contains("вчера")) {
            day.remove("вчера")
            var currentDate = LocalDate.now()
            currentDate = currentDate.minusDays(1)
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            day.add(currentDate.format(formatter))
        }

        if (day.contains("сегодня")) {
            day.remove("сегодня")
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            day.add(currentDate.format(formatter))
        }
        return day
    }
}