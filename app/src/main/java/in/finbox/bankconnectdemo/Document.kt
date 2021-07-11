package `in`.finbox.bankconnectdemo

import java.time.LocalDate
import java.time.format.DateTimeFormatter


// Months to subtract to get the six month old month
private const val MONTHS_TO_SUBTRACT = 6L

// Subtract a day to get the previous date
private const val DAYS_TO_SUBTRACT = 1L

/**
 * Return start and end date for the document
 */
fun getStartEndDate(): Document {
    // Calculate the current date
    val localDate = LocalDate.now().minusDays(DAYS_TO_SUBTRACT)
    // Calculate six month old date
    var oldLocalDate = localDate.minusMonths(MONTHS_TO_SUBTRACT)
    oldLocalDate = oldLocalDate.plusDays(DAYS_TO_SUBTRACT)

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    // Calculate End Date
    val endDate = localDate.format(formatter)
    // Calculate Start Date
    val startDate = oldLocalDate.format(formatter)

    // return start and end date
    return Document(startDate, endDate)
}

/**
 * Class contains document start and end date
 */
data class Document(val startDate: String, val endDate: String)