import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test

class LogAnalyzerTest {
    private val logs =
        listOf(
            LogEntry("2025-10-25T10:00:00", "INFO", "auth-service", "User logged in"),
            LogEntry("2025-10-25T10:01:00", "ERROR", "payment-service", "Payment failed"),
            LogEntry("2025-10-25T10:02:00", "WARN", "auth-service", "Multiple login attempts"),
            LogEntry("2025-10-25T10:03:00", "ERROR", "payment-service", "Database timeout"),
            LogEntry("2025-10-25T10:04:00", "INFO", "notification-service", "Email sent"),
        )

    @Test
    fun `countByLevel returns correct counts`() {
        val result = LogAnalyzer.countByLevel(logs)
        assertEquals(2, result["INFO"])
        assertEquals(2, result["ERROR"])
        assertEquals(1, result["WARN"])
    }

    @Test
    fun `findMostActiveService returns correct service`() {
        val result = LogAnalyzer.findMostActiveService(logs)
        // maxByOrNull by string will pick the lexicographically last service
        assertEquals("payment-service", result)
    }

    @Test
    fun `getErrorsForService returns all ERROR services`() {
        val result = LogAnalyzer.getErrorsForService(logs, "payment-service")
        assertEquals(listOf("payment-service", "payment-service"), result)
    }

    @Test
    fun `analyzeMessageLengths returns average message lengths`() {
        val result = LogAnalyzer.analyzeMessageLengths(logs) as Map<*, *>
        assertEquals(12.0, result["INFO"])
        assertEquals(15.0, result["ERROR"])
        assertEquals(23.0, result["WARN"])
    }

    @Test
    fun `getTopNLongestMessages returns correct number of longest logs`() {
        val result = LogAnalyzer.getTopNLongestMessages(logs, 2)
        assertEquals(2, result.size)
        assertTrue(result[0].message.length >= result[1].message.length)
    }

    @Test
    fun `filterByTimeRange returns logs within range`() {
        val result = LogAnalyzer.filterByTimeRange(logs, "2025-10-25T10:01:00", "2025-10-25T10:03:00")
        assertEquals(3, result.size)
        assertEquals("ERROR", result[0].level)
        assertEquals("WARN", result[1].level)
    }

    @Test
    fun `getServiceLevelDistribution returns nested counts`() {
        val result = LogAnalyzer.getServiceLevelDistribution(logs)
        assertEquals(1, result["auth-service"]!!["INFO"] ?: 0)
        assertEquals(1, result["auth-service"]!!["WARN"] ?: 0)
        assertEquals(2, result["payment-service"]!!["ERROR"] ?: 0)
        assertEquals(1, result["notification-service"]!!["INFO"] ?: 0)
    }
}
