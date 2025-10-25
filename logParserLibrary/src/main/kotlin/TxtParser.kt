import java.io.File
import java.io.FileNotFoundException

class TxtParser : Parser {
    override fun parseLogs(file: String): List<LogEntry> {
        val regex = """\[([^\]]+)]\s*\[([^\]]+)]\s*\[([^\]]+)]\s*(.+)""".toRegex()

        try {
            val fileLines = File(file).readLines()

            val logLines: List<LogEntry> =
                fileLines.mapNotNull { line ->
                    // почему тут предупреждение
                    regex
                        .matchEntire(line)
                        ?.destructured
                        ?.let { (timestamp, level, service, message) ->
                            LogEntry(timestamp, level, service, message)
                        }
                }
            return logLines
        } catch (e: FileNotFoundException) {
            println("Error: File '$file' not found")
            return ArrayList<LogEntry>()
        }
    }
}
