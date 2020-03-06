package censusanalyser;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UsCensusAdapter extends CensusAdapter{
    Map<String, CensusDTO> censusMap=null;
    List<CensusDTO> censusDTOList=null;

    @Override
    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) {
        censusMap = super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
        return censusMap;
    }
    private int loadIndianStateCodeData(Map<String, CensusDTO> censusMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, USCensusCSV.class);
            Iterable<IndiaStateCodeCSV> indiaStateCodeCSVS=()->censusCSVIterator;

            StreamSupport.stream(indiaStateCodeCSVS.spliterator(),false).filter(csvState-> censusMap.get(csvState)!=null).

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
