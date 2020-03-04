package censusanalyser;

import com.google.gson.Gson;
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
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    ICsvBuilder iCsvBuilder=CsvBuilderFactory.getObject();
    List<IndiaCensusCSV> censusCSVList=null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            censusCSVList = iCsvBuilder.getIndiaCensusCSVList(reader,IndiaCensusCSV.class);
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


    public String getStateWiseSortedCensusData()  {



            Comparator<IndiaCensusCSV> csvComparator=Comparator.comparing(census->census.state);
            this.sort(censusCSVList,csvComparator);
            String json=new Gson().toJson(censusCSVList);
            return json;


    }

    private void sort(List<IndiaCensusCSV> censusCSVList, Comparator<IndiaCensusCSV> csvComparator) {
        for(int i=0;i<censusCSVList.size()-1;i++){
            for(int j=0;j<censusCSVList.size()-i-1;j++) {
                IndiaCensusCSV censusCSV1=censusCSVList.get(j);
                IndiaCensusCSV censusCSV2=censusCSVList.get(j+1);
                if(csvComparator.compare(censusCSV1,censusCSV2)>0){
                    censusCSVList.set(j,censusCSV2);
                    censusCSVList.set(j+1,censusCSV1);
                }
            }
        }
    }
}
