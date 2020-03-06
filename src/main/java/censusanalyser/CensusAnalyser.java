package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    public enum Country{
        INDIA,US;
    }

    Map<String, CensusDTO> censusMap;
    List<CensusDTO> censusDTOList;
    Map<SortField,Comparator<CensusDTO>> sortMap;

    public CensusAnalyser() {

        censusMap = new HashMap<>();
        sortMap=new HashMap<>();
        this.sortMap.put(SortField.STATE,Comparator.comparing(census->census.state));
        this.sortMap.put(SortField.POPULATION,Comparator.comparing(census->census.population));
        this.sortMap.put(SortField.POPULATIONDENSITY,Comparator.comparing(census->census.densityPerSqKm));
        this.sortMap.put(SortField.TOTALAREA,Comparator.comparing(census->census.areaInSqKm));
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap =CensusAdapterFactory.getCensusAdapter(country,csvFilePath);
        censusDTOList=censusMap.values().stream().collect(Collectors.toList());
        return censusDTOList.size();
    }


    public String getSortedCensusData(SortField population) {
        if (censusDTOList.size() == 0 || censusDTOList == null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        // Comparator<CensusDTO> censusCSVComparator = Comparator.comparing(census -> census.population);
        Comparator<CensusDTO> censusCSVComparator = sortMap.get(population);
        this.sort(censusCSVComparator);
        String sortedPopulationCensusJson = new Gson().toJson(censusDTOList);
        return sortedPopulationCensusJson;
    }

    private void sort(Comparator<CensusDTO> censusCSVComparator) {

        for (int i = 0; i < censusDTOList.size() - 1; i++) {
            for (int j = 0; j < censusDTOList.size() - i - 1; j++) {
                CensusDTO census1 = censusDTOList.get(j);
                CensusDTO census2 = censusDTOList.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusDTOList.set(j, census2);
                    censusDTOList.set(j + 1, census1);
                }
            }
        }
    }
}
