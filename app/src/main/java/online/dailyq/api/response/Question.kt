package online.dailyq.api.response

import java.time.LocalDate
import java.util.Date

data class Question (
    val id: LocalDate,
    var text: String,
    val answerCount: Int,
    val updatedAt: Date,
    val cratedAi: Date
)