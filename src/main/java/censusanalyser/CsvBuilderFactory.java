package censusanalyser;

public class CsvBuilderFactory {

    public static ICsvBuilder getObject()
    {
        return new CsvBuilderClass();
    }
}
