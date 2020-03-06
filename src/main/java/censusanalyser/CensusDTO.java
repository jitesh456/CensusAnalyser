package censusanalyser;

public class CensusDTO {
    public String state;
    public double population;
    public double areaInSqKm;
    public double densityPerSqKm;
    public  String stateCode;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;

    }

    public CensusDTO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        population = usCensusCSV.population;
        areaInSqKm =  usCensusCSV.totalArea;
        densityPerSqKm = usCensusCSV.populationDensity;
        stateCode = usCensusCSV.stateId;
    }
}
