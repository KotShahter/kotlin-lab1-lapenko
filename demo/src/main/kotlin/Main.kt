package org.example

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun main() {
    val levels = listOf("INFO", "WARN", "ERROR")
    val services = listOf(
        "auth-service",
        "payment-service",
        "notification-service",
        "user-service",
        "cache-service",
        "gateway-service",
        "database-service",
        "audit-service",
        "report-service"
    )
    val messages = listOf(
        "User logged in",
        "Payment gateway timeout",
        "Multiple login attempts",
        "Email sent",
        "Database connection failed",
        "Invalid credentials",
        "Profile updated",
        "Cache miss for key user_%d",
        "Insufficient funds",
        "SMS queued",
        "Session refreshed",
        "High latency detected (%dms)",
        "Null pointer while fetching preferences",
        "Transaction completed",
        "Email retry #%d",
        "Token expired for user ID %d",
        "Log rotation completed",
        "Downstream service unavailable",
        "Deadlock detected on table `users`",
        "User logged out",
        "Currency conversion failed (USD→JPY)",
        "Push notification delivered",
        "Memory usage above 85%",
        "Unexpected token format in header",
        "Daily report generated",
        "Password about to expire for user ID %d",
        "Upstream timeout (service: analytics)"
    )
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val random = Random(System.currentTimeMillis())

    val logFile = File("examples\\logs.txt")
    logFile.bufferedWriter().use { writer ->
        repeat(30) { // generate 30 log lines
            val timestamp = LocalDateTime.now()
                .plusSeconds(it * random.nextLong(10, 60))
                .format(formatter)
            val level = levels.random()
            val service = services.random()
            val messageTemplate = messages.random()
            val message = if (messageTemplate.contains("%d")) {
                messageTemplate.format(random.nextInt(1, 1000))
            } else messageTemplate

            writer.write("[$timestamp] [$level] [$service] $message")
            writer.newLine() // go to next line
        }
    }
}