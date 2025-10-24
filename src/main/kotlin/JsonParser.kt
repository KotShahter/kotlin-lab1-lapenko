import java.io.File
import kotlinx.serialization.json.*


class JsonParser : Parser {
    override fun ParseLogs (file: String): List<LogEntry>{

        val fileContent = File(file).readText()
        val logLines = Json.decodeFromString<List<LogEntry>>(fileContent)

        return logLines;
    }
}