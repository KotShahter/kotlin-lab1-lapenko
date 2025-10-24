object LogParserFactory {
    fun createParserForFile(format: String): Parser =
        when {
            format.endsWith("json") -> JsonParser()
            format.endsWith("txt") -> TxtParser()
            else -> throw IllegalArgumentException("Unsupported format: $format")
        }
}
