package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.sun.jdi.ClassType;
import com.sun.jdi.InvocationException;
import com.sun.tools.javac.code.Attribute;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    ICsvBuilder iCsvBuilder=CsvBuilderFactory.getObject();
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){

            List<IndiaCensusCSV> censusCSVList = iCsvBuilder.getIndiaCensusCSVList(reader,IndiaCensusCSV.class);
            return censusCSVList.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (Exception e)
        {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.WRONG_DELIMETER);
        }
    }



    public int loadStateCode(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){

            Iterator<IndiaStateCodeCSV> censusCSVIterator =iCsvBuilder.getIndiaCensusCSVIterator(reader,IndiaStateCodeCSV.class);
            return getCount(censusCSVIterator);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private   <E> int  getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> indiaCensusCSVS = () -> censusCSVIterator;
        int namOfEateries = (int) StreamSupport.stream(indiaCensusCSVS.spliterator(), false).count();
        return namOfEateries;
    }



}
