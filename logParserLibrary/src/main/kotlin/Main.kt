fun main() {
    val myJsonParser = LogParserFactory.createParserForFile("examples\\exampleLogs.json")
    val myTxtParser = LogParserFactory.createParserForFile("examples\\exampleLogs.txt")

    val myLogs = myJsonParser.parseLogs("examples\\exampleLogs.json")
    val myLogs2 = myTxtParser.parseLogs("examples\\exampleLogs.txt")

    println(LogAnalyzer.countByLevel(myLogs))
    println(LogAnalyzer.findMostActiveService(myLogs))
    println(LogAnalyzer.getErrorsForService(myLogs, "123"))
    println(LogAnalyzer.getTopNLongestMessages(myLogs, 2))
    println()
    println(LogAnalyzer.analyzeMessageLengths(myLogs))
    println()
    println(LogAnalyzer.filterByTimeRange(myLogs, "2024-01-15T10:28:17", "2024-01-15T10:32:12"))
}
