package org.example

import LogParserFactory
import LogAnalyzer

fun main() {
    TxtGenerator.generate()
    JsonGenerator.generateLogsToFile(30, "examples\\exampleLogs.json")

    val myJsonParser = LogParserFactory.createParserForFile("examples\\json_logs.json")
    val myTxtParser = LogParserFactory.createParserForFile("examples\\exampleLogs.txt")

    val myLogs = myJsonParser.parseLogs("examples\\exampleLogs.json")
    val myLogs2 = myTxtParser.parseLogs("examples\\exampleLogs.txt")

    println("~~~ TXT Logs ~~~")

    println(LogAnalyzer.countByLevel(myLogs2))
    println(LogAnalyzer.findMostActiveService(myLogs2))
    println(LogAnalyzer.getErrorsForService(myLogs2, "123"))
    println(LogAnalyzer.getTopNLongestMessages(myLogs2, 2))
    println()
    println(LogAnalyzer.analyzeMessageLengths(myLogs2))
    println()
    println(LogAnalyzer.filterByTimeRange(myLogs2, "2024-01-15T10:28:17", "2024-01-15T10:32:12"))

    println("~~~ Json Logs ~~~")

    println(LogAnalyzer.countByLevel(myLogs))
    println(LogAnalyzer.findMostActiveService(myLogs))
    println(LogAnalyzer.getErrorsForService(myLogs, "123"))
    println(LogAnalyzer.getTopNLongestMessages(myLogs, 2))
    println()
    println(LogAnalyzer.analyzeMessageLengths(myLogs))
    println()
    println(LogAnalyzer.filterByTimeRange(myLogs, "2024-01-15T10:28:17", "2024-01-15T10:32:12"))
}
