data class LogEntry(
    val timestamp: String,
    val level: String,
    val service: String,
    val message: String
)

interface LogAnalyzer {
    fun countByLevel(): Map<String, Int>
    fun findMostActiveService(): String?
    fun getErrorsForService(serviceName: String): List<String>
    fun analyzeMessageLengths(): Map<String, Double>
    fun getTopNLongestMessages(n: Int): List<LogEntry>
    fun filterByTimeRange(startTime: String, endTime: String): List<LogEntry>
    fun getServiceLevelDistribution(): Map<String, Map<String, Int>>
}

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

fun parsing()
{
    val loglines: List<LogEntry> = sampleLogs.mapNotNull { line ->
        regex.matchEntire(line)?.destructured?.let { (timestamp, level, service, message) ->
            LogEntry(timestamp, level, service, message)}

    val (log1, log2, log3, log4) = regex.matchEntire("[2024-01-15T10:23:45] [INFO] [auth-service] User logged in")!!.destructured
    val temp = LogEntry(log1, log2, log3, log4)

   // val parsedLogs = sampleLogs.mapNotNull {LogEntry(regex.matchEntire(it).destructured)}

}


fun main()
{
    print(123) //eachCount
    parsing()
}