import java.io.File
import kotlinx.serialization.json.*


class JsonParser : Parser {
    override fun ParseLogs (file: File): List<LogEntry>{
        val logLines: List<LogEntry> = ArrayList <LogEntry>()
        return logLines;
    }
}