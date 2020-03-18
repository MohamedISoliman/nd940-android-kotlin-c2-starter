package com.udacity.asteroidradar

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * Created by Mohamed Ibrahim on 3/18/20.
 */

fun String.toDate(format: String): Date? {
    return SimpleDateFormat(format, Locale.ENGLISH).parse(this)
}

fun Date.formattedString(format: String): String {
    val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
    return dateFormat.format(this)
}

fun nextFormattedDateList(
    startDate: Date = Calendar.getInstance().time,
    count: Int = Constants.DEFAULT_END_DATE_DAYS,
    format: String = Constants.API_QUERY_DATE_FORMAT
): List<String> {
    val formattedDateList = ArrayList<String>()
    val calendar = Calendar.getInstance()
    calendar.time = startDate
    for (i in 0 until count) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        val strDate: String = dateFormat.format(calendar.time)
        formattedDateList.add(strDate)
    }

    return formattedDateList
}