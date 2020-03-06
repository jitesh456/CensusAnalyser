package censusanalyser;

import java.util.Map;

public class CensusAdapterFactory {

    public static Map<String,CensusDTO> getCensusAdapter(CensusAnalyser.Country country,String ... csvFilePath) {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return new IndianCensusLoader().loadCensusData(csvFilePath);
        else if(country.equals(CensusAnalyser.Country.US))
            return new UsCensusAdapter().loadCensusData(csvFilePath);
        else
            throw new CensusAnalyserException("Wrong class name",CensusAnalyserException.ExceptionType.INVALID_CLASS);
    }
}
