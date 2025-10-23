import kotlinx.serialization.json.*


data class LogEntry(
    val timestamp: String,
    val level: String,
    val service: String,
    val message: String
)

val sampleLogs = listOf(
    "[2024-01-15T10:23:45] [INFO] [auth-service] User logged in",
    "[2024-01-15T10:24:12] [ERROR] [payment-service] Payment gateway timeout",
    "[2024-01-15T10:24:30] [WARN] [auth-service] Multiple login attempts",
    "[2024-01-15T10:25:01] [INFO] [notification-service] Email sent",
    "[2024-01-15T10:25:45] [ERROR] [payment-service] Database connection failed",
    "[2024-01-15T10:27:00] [ERROR] [auth-service] Invalid credentials",
    "[2024-01-15T10:27:34] [INFO] [user-service] Profile updated",
    "[2024-01-15T10:28:02] [WARN] [cache-service] Cache miss for key user_2381",
    "[2024-01-15T10:28:17] [ERROR] [payment-service] Insufficient funds",
    "[2024-01-15T10:28:45] [INFO] [notification-service] SMS queued",
    "[2024-01-15T10:29:11] [INFO] [auth-service] Session refreshed",
    "[2024-01-15T10:29:48] [WARN] [gateway-service] High latency detected (782ms)",
    "[2024-01-15T10:30:05] [ERROR] [user-service] Null pointer while fetching preferences",
    "[2024-01-15T10:31:00] [INFO] [payment-service] Transaction completed",
    "[2024-01-15T10:31:27] [WARN] [notification-service] Email retry #2",
    "[2024-01-15T10:32:12] [ERROR] [auth-service] Token expired for user ID 504",
    "[2024-01-15T10:33:45] [INFO] [audit-service] Log rotation completed",
    "[2024-01-15T10:34:02] [WARN] [gateway-service] Downstream service unavailable",
    "[2024-01-15T10:34:37] [ERROR] [database-service] Deadlock detected on table `users`",
    "[2024-01-15T10:35:19] [INFO] [auth-service] User logged out",
    "[2024-01-15T10:35:55] [ERROR] [payment-service] Currency conversion failed (USD→JPY)",
    "[2024-01-15T10:36:11] [INFO] [notification-service] Push notification delivered",
    "[2024-01-15T10:37:00] [WARN] [cache-service] Memory usage above 85%",
    "[2024-01-15T10:37:38] [ERROR] [auth-service] Unexpected token format in header",
    "[2024-01-15T10:38:12] [INFO] [report-service] Daily report generated",
    "[2024-01-15T10:38:40] [WARN] [auth-service] Password about to expire for user ID 901",
    "[2024-01-15T10:39:15] [ERROR] [gateway-service] Upstream timeout (service: analytics)"
)

val regex = """\[([^\]]+)]\s*\[([^\]]+)]\s*\[([^\]]+)]\s*(.+)""".toRegex()

class LogAnalyzer (LogList : List<LogEntry>){

    fun countByLevel(logList: List<LogEntry>): Map<String, Int> {
        val groupedByFirstLetter = logList.groupingBy { it.level } //мамой клянусь, я офигеть как понимаю что это
        val counts = groupedByFirstLetter.eachCount()
        return counts;
    }

    fun findMostActiveService(logList: List<LogEntry>): String {
        val result =
            logList.maxByOrNull { it.service } // типа оно выдает первый максимальный элемент sqlной функции. все это оч похоже на бд
        return result!!.service;
    }

    fun getErrorsForService(logList: List<LogEntry>, service: String): List<String> {
        val groupedByFirstLetter = logList.groupBy { it.level }
        return groupedByFirstLetter["ERROR"]!!.map { it.service }; //фп прикольное
    }

    fun analyzeMessageLengths(logList: List<LogEntry>): Any {
        val lengths = logList.groupBy { it.level }.mapValues { it -> it.value.map { it.message.length }.average() }
        return lengths
    }

    fun getTopNLongestMessages(logList: List<LogEntry>, TopN: Int): List<LogEntry> {
        val groupedByFirstLetter = logList.sortedByDescending { it.toString().length }.take(TopN)
        return groupedByFirstLetter.map { it }
    }

    fun filterByTimeRange(logList: List<LogEntry>, timestamp1: String, timestamp2: String): List<LogEntry> {
        val groupedByFirstLetter = logList.filter { timestamp1 < it.timestamp && it.timestamp < timestamp2 }
        return groupedByFirstLetter
    }

    fun getServiceLevelDistribution(logList: List<LogEntry>): Map<String, Map<String, Int>> {
        val groupedByFirstLetter =
            logList.groupBy { it.service }.mapValues { entry -> entry.value.groupingBy { it.level }.eachCount() }
        return groupedByFirstLetter
    }
}

fun parsing(): List<LogEntry> {
    val loglines: List<LogEntry> = sampleLogs.mapNotNull { line -> //разобрано, поясню что тут и куда
        regex.matchEntire(line)?.destructured?.let { (timestamp, level, service, message) ->
            LogEntry(timestamp, level, service, message)
        }
    }
    return loglines
}
// val parsedLogs = sampleLogs.mapNotNull {LogEntry(regex.matchEntire(it).destructured)}


fun main() {

    val analyzer = LogAnalyzer(parsing())
    val myLogs = parsing()
    println(analyzer.countByLevel(myLogs))
    println(analyzer.findMostActiveService(myLogs))
    println(analyzer.getErrorsForService(myLogs, "123"))
    //println(analyzeMessageLengths(MyLogs))
    println(analyzer.getTopNLongestMessages(myLogs, 2))
    println()
    println(analyzer.analyzeMessageLengths(myLogs))
    println()
    println(analyzer.filterByTimeRange(myLogs, "2024-01-15T10:28:17", "2024-01-15T10:32:12"))
}

