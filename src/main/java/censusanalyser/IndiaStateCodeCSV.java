package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column="StateName")
    private String statName;
    @CsvBindByName(column="State")
    private String state;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "stateCode='" + statName + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
