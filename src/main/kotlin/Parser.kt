import java.io.File

interface Parser {
    fun ParseLogs (file: String): List<LogEntry>
}