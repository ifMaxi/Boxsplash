package com.maxidev.boxsplash.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    fun convertDateTime(dateTime: String): String {
         return try {
             val inputFormat = ZonedDateTime.parse(dateTime)
             val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm")

             inputFormat.format(formatter)
         } catch (e: Exception) {
             e.printStackTrace()
             ""
         }
    }
}