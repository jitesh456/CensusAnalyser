package censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICsvBuilder <E>{
    public Iterator<E> getIndiaCensusCSVIterator(Reader reader, Class csvType) throws CsvBuilderException;
    public List<E> getIndiaCensusCSVList(Reader reader, Class csvType) throws CsvBuilderException;
}
