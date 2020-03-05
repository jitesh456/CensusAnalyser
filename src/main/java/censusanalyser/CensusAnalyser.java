package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    Map<String, CensusDTO> censusMap;
    List<CensusDTO> censusDTOList;
    CensusCSVLoad censusCSVLoad=new CensusCSVLoad();
    public CensusAnalyser() {
        censusMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusMap =censusCSVLoad.loadCensusData(IndiaCensusCSV.class,csvFilePath);
        censusDTOList=censusMap.values().stream().collect(Collectors.toList());
        return censusDTOList.size();
    }




    public String getStateWiseSortedCensusData(String csvFilePath) {
        if (censusDTOList.size() == 0 || censusDTOList == null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDTO> censusCSVComparator = Comparator.comparing(census -> census.state);
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDTOList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<CensusDTO> censusCSVComparator) {

        for (int i = 0; i < censusDTOList.size() - 1; i++) {
            for (int j = 0; j < censusDTOList.size() - i - 1; j++) {
                CensusDTO census1 = censusDTOList.get(j);
                CensusDTO census2 =
                        censusDTOList.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusDTOList.set(j, census2);
                    censusDTOList.set(j + 1, census1);
                }
            }
        }
    }

    public int loadUsCensusData(String csvFilePath) {
        Map<String, CensusDTO> censusDTOMap = censusCSVLoad.loadCensusData(USCensusCSV.class,csvFilePath);
        return censusDTOMap.size();
    }
}
