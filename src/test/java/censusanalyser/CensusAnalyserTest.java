package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData (1).csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSV_ShouldReturnExactCount() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int numOfStateCode=censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
        Assert.assertEquals(29, numOfStateCode);
    }

    @Test
    public void givenIndiaCensusData_WhenSorted_ShouldReturnSortedData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(SortField.STATE);
            IndiaStateCodeCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUsCensusData_WhenProper_ShouldReturnCorrectcount() {
        CensusAnalyser censusAnalyser=new CensusAnalyser();
        int usSateCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51,usSateCount);
    }

    @Test
    public void givenIndiaCensusData_WhenProper_ShouldReturnSortedData() {
        CensusAnalyser censusAnalyser=new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getSortedCensusData(SortField.POPULATION);
        CensusDTO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDTO[].class);
        Assert.assertEquals(199812341,censusCSV[28].population,0.0);
    }

    @Test
    public void givenUsCensusData_WhenProper_ShouldReturnSortedData(){
        CensusAnalyser censusAnalyser=new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedCensusData = censusAnalyser.getSortedCensusData(SortField.POPULATION);
        CensusDTO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDTO[].class);
        Assert.assertEquals(37253956,censusCSV[censusCSV.length-1].population,0.0);
    }

    @Test
    public void givenUsCensusDataAndIndiaCensusData_WhenproperReturn_ShouldReturnSortedData() {
        CensusAnalyser censusAnalyser=new CensusAnalyser();

        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
        String sortedCensusDataOfIndia = censusAnalyser.getSortedCensusData(SortField.POPULATION);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusDataOfIndia, CensusDTO[].class);


        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusDataOfUs = censusAnalyser.getSortedCensusData(SortField.POPULATION);
        CensusDTO[] censusCSV2 = new Gson().fromJson(sortedCensusDataOfUs, CensusDTO[].class);
        censusAnalyser.getSortedCensusData(SortField.POPULATION);
        Assert.assertEquals(37253956,censusCSV2[censusCSV2.length-1].population,0.0);
        Assert.assertEquals(199812341,censusCSV[censusCSV.length-1].population,0.0);


    }
}
