package censusanalyser;

public class CsvBuilderException extends  RuntimeException {
    public CsvBuilderException(String message, String name) {
    }

    enum ExceptionType {
        UNABLE_TO_PARSE,

    }
    ExceptionType type;

    public CsvBuilderException(String message,ExceptionType type) {
        super(message);
        this.type = type;
    }
}
