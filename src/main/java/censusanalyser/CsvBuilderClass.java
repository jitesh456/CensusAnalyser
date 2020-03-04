package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public  class CsvBuilderClass <E>implements ICsvBuilder  {
    public    Iterator<E> getIndiaCensusCSVIterator(Reader reader, Class csvType) throws CsvBuilderException {
        try {
            return getCsvBean(reader,csvType).iterator();
        }
        catch (IllegalStateException e) {
            throw new CsvBuilderException(e.getMessage(),CsvBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
    @Override
    public List<E> getIndiaCensusCSVList(Reader reader, Class csvType) throws CsvBuilderException {
        return getCsvBean(reader,csvType).parse();
    }

    public CsvToBean<E> getCsvBean(Reader reader,Class csvClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return  csvToBean;
    }
}
