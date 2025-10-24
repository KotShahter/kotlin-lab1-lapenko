import kotlinx.serialization.json.Json
import java.io.File

class JsonParser : Parser {
    override fun parseLogs(file: String): List<LogEntry> {
        val fileContent = File(file).readText()
        val logLines = Json.decodeFromString<List<LogEntry>>(fileContent)

        return logLines
    }
}
