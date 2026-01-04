package pl.emilia.kura.bontrackpetite.model

import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean
)
