import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class LogEntry(
    val timestamp: String,
    val level: String,
    val service: String,
    val message: String,
)

object JsonGenerator {
    private val json = Json { prettyPrint = true }
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    private val services =
        listOf(
            "auth-service",
            "payment-service",
            "notification-service",
            "user-service",
            "cache-service",
            "gateway-service",
            "database-service",
            "audit-service",
            "report-service",
        )

    private val levels = listOf("INFO", "WARN", "ERROR")

    private val authMessages =
        listOf(
            "User logged in",
            "User logged out",
            "Session refreshed",
            "Invalid credentials",
            "Token expired for user ID",
            "Unexpected token format in header",
            "Multiple login attempts",
            "Password about to expire for user ID",
        )

    private val paymentMessages =
        listOf(
            "Payment gateway timeout",
            "Database connection failed",
            "Insufficient funds",
            "Currency conversion failed",
            "Transaction completed",
        )

    private val otherMessages =
        listOf(
            "Email sent",
            "SMS queued",
            "Push notification delivered",
            "Profile updated",
            "Cache miss for key",
            "High latency detected",
            "Null pointer while fetching preferences",
            "Email retry",
            "Log rotation completed",
            "Downstream service unavailable",
            "Deadlock detected on table",
            "Memory usage above",
            "Daily report generated",
            "Upstream timeout",
        )

    fun generateLogEntry(
        service: String? = null,
        level: String? = null,
    ): LogEntry {
        val selectedService = service ?: services.random()
        val selectedLevel = level ?: levels.random()
        val timestamp = LocalDateTime.now().format(formatter)

        val message =
            when (selectedService) {
                "auth-service" -> authMessages.random()
                "payment-service" -> paymentMessages.random()
                else -> otherMessages.random()
            }.let { baseMessage ->
                // Add some dynamic content
                when {
                    baseMessage.contains("user ID") -> "$baseMessage ${(100..999).random()}"
                    baseMessage.contains("key") -> "$baseMessage user_${(1000..9999).random()}"
                    baseMessage.contains("latency") -> "$baseMessage (${(100..1500).random()}ms)"
                    baseMessage.contains("Currency conversion failed") ->
                        "$baseMessage (${listOf("USD→EUR", "EUR→GBP", "USD→JPY", "GBP→USD").random()})"
                    baseMessage.contains("table") -> "$baseMessage `${listOf("users", "payments", "sessions", "logs").random()}`"
                    baseMessage.contains("usage above") -> "$baseMessage ${(75..95).random()}%"
                    baseMessage.contains("Email retry") -> "$baseMessage #${(1..5).random()}"
                    else -> baseMessage
                }
            }

        return LogEntry(timestamp, selectedLevel, selectedService, message)
    }

    fun generateLogs(
        count: Int,
        service: String? = null,
        level: String? = null,
    ): List<LogEntry> =
        List(count) {
            generateLogEntry(service, level)
        }

    fun generateLogsToFile(
        count: Int,
        filename: String,
        service: String? = null,
        level: String? = null,
    ) {
        val logs = generateLogs(count, service, level)
        val jsonString = json.encodeToString(logs)
        File(filename).writeText(jsonString)
        println("Generated $count logs to $filename")
    }
}
