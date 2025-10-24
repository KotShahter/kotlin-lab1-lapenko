import java.io.File

interface Parser {
    fun ParseLogs (file: File): List<LogEntry>
}