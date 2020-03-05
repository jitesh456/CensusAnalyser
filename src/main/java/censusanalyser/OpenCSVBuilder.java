package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder {
    public Iterator<ICSVBuilder> getCSVFileIterator(Reader reader, Class csvclass) {
        return this.getCSVToBean(reader, csvclass).iterator();
    }

    @Override
    public List<ICSVBuilder> getCSVFileList(Reader reader, Class csvclass) {
        return this.getCSVToBean(reader, csvclass).parse();
    }

    private CsvToBean getCSVToBean(Reader reader, Class csvClass) {
        try {
            CsvToBeanBuilder<ICSVBuilder> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<ICSVBuilder> csvToBean = csvToBeanBuilder.build();
            return csvToBean;
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
