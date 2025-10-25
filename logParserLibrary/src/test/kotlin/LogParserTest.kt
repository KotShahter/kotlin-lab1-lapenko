
import kotlinx.serialization.MissingFieldException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.io.path.createTempFile
import kotlin.io.path.pathString
import kotlin.io.path.writeText

class LogParserTest {

    private val txtParser = TxtParser()
    private val jsonParser = JsonParser()

    val tempFile = createTempFile("test_logs", ".txt")

    private val sampleTxtLogs = """
        [2025-10-25T10:00:00] [INFO] [auth-service] User logged in
        [2025-10-25T10:01:00] [ERROR] [payment-service] Payment failed
        [2025-10-25T10:02:00] [WARN] [auth-service] Multiple login attempts
    """.trimIndent()

    val tempFileJS = createTempFile("test_logs", ".json")
    private val sampleJsonLogs = """
        [
            {"timestamp":"2025-10-25T10:00:00","level":"INFO","service":"auth-service","message":"User logged in"},
            {"timestamp":"2025-10-25T10:01:00","level":"ERROR","service":"payment-service","message":"Payment failed"},
            {"timestamp":"2025-10-25T10:02:00","level":"WARN","service":"auth-service","message":"Multiple login attempts"}
        ]
    """.trimIndent()

    // ---------------- TXT Parser ----------------
    @Test
    fun `TXT parser parses logs correctly`() {
        tempFile.writeText(sampleTxtLogs)
        val logs = txtParser.parseLogs(tempFile.pathString)
        assertEquals(3, logs.size)
        assertEquals("INFO", logs[0].level)
        assertEquals("payment-service", logs[1].service)
        assertEquals("Multiple login attempts", logs[2].message)
    }

    @Test
    fun `TXT parser handles empty input`() {
        val logs = txtParser.parseLogs("")
        assertTrue(logs.isEmpty())
    }

    @Test
    fun `TXT parser handles malformed line`() {
        val malformed = "[2025-10-25T10:00:00] [INFO] User logged in" // missing service
        val logs = txtParser.parseLogs(malformed)
        assertTrue(logs.isEmpty() || logs[0].service.isEmpty())
    }

    // ---------------- JSON Parser ----------------
    @Test
    fun `JSON parser parses logs correctly`() {
        tempFileJS.writeText(sampleJsonLogs)
        val logs = jsonParser.parseLogs(tempFileJS.pathString)
        assertEquals(3, logs.size)
        assertEquals("INFO", logs[0].level)
        assertEquals("payment-service", logs[1].service)
        assertEquals("Multiple login attempts", logs[2].message)
    }

    @Test
    fun `JSON parser handles empty array`() {
        val exception = assertThrows(Exception::class.java) {
            val logs = jsonParser.parseLogs("[]")
        }
        assertNotNull(exception.message)
    }

    @Test
    fun `JSON parser handles malformed JSON`() {
        val malformed = "[{bad json}]"
        val exception = assertThrows(Exception::class.java) {
            jsonParser.parseLogs(malformed)
        }
        assertNotNull(exception.message)
    }

    @Test
    fun `JSON parser handles missing fields`() {
        val jsonWithMissingFields = """
        [
            {"timestamp":"2025-10-25T10:00:00","level":"INFO","message":"No service field"}
        ]
    """.trimIndent()
        tempFileJS.writeText(jsonWithMissingFields)

        val exception = assertThrows(Exception::class.java) {
            val logs = jsonParser.parseLogs(tempFileJS.pathString)
        }
        assertNotNull(exception.message)
    }
}