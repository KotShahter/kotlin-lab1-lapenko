object LogParserFactory {
    fun createParser(format: String): Parser {
        return when  {
            format.endsWith("json") -> JsonParser()
            format.endsWith("txt") -> TxtParser()
            else -> throw IllegalArgumentException("Unsupported format: $format")
        }
    }
}