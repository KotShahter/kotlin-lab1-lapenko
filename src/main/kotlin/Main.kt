fun main() {

    val myJsonParser = LogParserFactory.createParser("json")
    val myTxtParser = LogParserFactory.createParser("text")

    val myLogs = myJsonParser.ParseLogs("examples\\exampleLogs.json")
    val myLogs2 = myTxtParser.ParseLogs("examples\\exampleLogs.json")

    println(LogAnalyzer.countByLevel(myLogs))
    println(LogAnalyzer.findMostActiveService(myLogs))
    println(LogAnalyzer.getErrorsForService(myLogs, "123"))
    //println(analyzeMessageLengths(MyLogs))
    println(LogAnalyzer.getTopNLongestMessages(myLogs, 2))
    println()
    println(LogAnalyzer.analyzeMessageLengths(myLogs))
    println()
    println(LogAnalyzer.filterByTimeRange(myLogs, "2024-01-15T10:28:17", "2024-01-15T10:32:12"))
}

