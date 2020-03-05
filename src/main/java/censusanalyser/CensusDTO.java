package censusanalyser;

public class CensusDTO {
    public String state;
    public long population;
    public long areaInSqKm;
    public long densityPerSqKm;
    public  String stateCode;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;

    }

    public CensusDTO(USCensusCSV usCensusCSV) {
        this.state = state;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.densityPerSqKm = densityPerSqKm;
        this.stateCode = stateCode;
    }
}
