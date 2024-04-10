package online.dailyq.api.response

import java.time.LocalDate
import java.util.Date

data class Answer (
    val qid: LocalDate,
    val uid: String,
    val text: String?,
    val photo: String?,
    val updatedAt: Date,
    val cratedAt: Date
)