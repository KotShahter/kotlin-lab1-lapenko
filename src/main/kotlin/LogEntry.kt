import kotlinx.serialization.Serializable

@Serializable
data class LogEntry(
    val timestamp: String,
    val level: String,
    val service: String,
    val message: String
)