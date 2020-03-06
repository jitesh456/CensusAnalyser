package censusanalyser;

import sun.management.LazyCompositeData;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IndianCensusLoader extends CensusAdapter {

    Map<String, CensusDTO> censusMap=null;
    List<CensusDTO> censusDTOList=null;
    @Override
    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) {
        censusMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        this.loadIndianStateCodeData(censusMap,csvFilePath[1]);
        return censusMap;
    }
   /* public  Map<String, CensusDTO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) {
        if(country.equals(CensusAnalyser.Country.INDIA))
        {
            return this.loadCensusData(IndiaCensusCSV.class ,csvFilePath);
        }
        else if(country.equals(CensusAnalyser.Country.US))
        {
            return this.loadCensusData(USCensusCSV.class ,csvFilePath);
        }
        else
            throw new CensusAnalyserException("Wrong class name",CensusAnalyserException.ExceptionType.INVALID_CLASS);
    }
    private  <E> Map<String, CensusDTO> loadCensusData(Class<E> csvClass, String... csvFilePath) {
        censusMap=new HashMap<>();
        censusDTOList=new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, csvClass);
            Iterable<E> csvIterable=()->censusCSVIterator;
            if(csvClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(csvState -> censusMap.put(csvState.state, new CensusDTO(csvState)));
            }
            else if(csvClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(csvState -> censusMap.put(csvState.state, new CensusDTO(csvState)));
            }
            censusDTOList = censusMap.values().stream().collect(Collectors.toList());
            if(csvFilePath.length==1){
            return censusMap;}
            this.loadIndianStateCodeData(censusMap,csvFilePath[1]);
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }*/
    private int loadIndianStateCodeData(Map<String, CensusDTO> censusMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> indiaStateCodeCSVS=()->censusCSVIterator;

            StreamSupport.stream(indiaStateCodeCSVS.spliterator(),false).filter(csvState-> this.censusMap.get(csvState)!=null).

                    forEach(csvState-> this.censusMap.get(csvState).stateCode=csvState.stateCode);
          /*  while (censusCSVIterator.hasNext()) {
                IndiaStateCodeCSV indiaStateCodeCSV = censusCSVIterator.next();
                IndiaCensusDTO indiaCensusDTO = censusMap.get(indiaStateCodeCSV.state);
                if (indiaCensusDTO==null)
                    continue;
            }*/
            censusDTOList= this.censusMap.values().stream().collect(Collectors.toList());

            return censusDTOList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }



}
