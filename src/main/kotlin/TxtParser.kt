import java.io.File

class TxtParser : Parser {
    override fun ParseLogs (file: String): List<LogEntry>{

        val regex = """\[([^\]]+)]\s*\[([^\]]+)]\s*\[([^\]]+)]\s*(.+)""".toRegex()
        val fileLines = File(file).readLines() //TODO EXCEPTION CHECK IF EXISTS

        val logLines: List<LogEntry> = fileLines.mapNotNull { line -> // почему тут предупреждение
            regex.matchEntire(line)?.destructured?.
                let { (timestamp, level, service, message) ->
                    LogEntry(timestamp, level, service, message)
                }
            }

        return logLines;
    }
}