object LogAnalyzer {
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