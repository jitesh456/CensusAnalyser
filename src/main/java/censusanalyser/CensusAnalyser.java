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
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {


        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){

            Iterator<IndiaCensusCSV> censusCSVIterator = getIndiaCensusCSVIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> indiaCensusCSVS = () -> censusCSVIterator;
            int namOfEateries = (int) StreamSupport.stream(indiaCensusCSVS.spliterator(), false).count();
            return namOfEateries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (Exception e)
        {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.WRONG_DELIMETER);
        }
    }

    private <E>Iterator<E> getIndiaCensusCSVIterator(Reader reader, Class csvType) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvType);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }

    public int loadStateCode(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){

            Iterator<IndiaStateCodeCSV> censusCSVIterator =getIndiaCensusCSVIterator(reader,IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> indiaCensusCSVS = () -> censusCSVIterator;
            int namOfEateries = (int) StreamSupport.stream(indiaCensusCSVS.spliterator(), false).count();
            return namOfEateries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }


}
