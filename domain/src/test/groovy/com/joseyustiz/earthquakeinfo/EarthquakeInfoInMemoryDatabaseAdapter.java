package com.joseyustiz.earthquakeinfo;

import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDate.parse;

public class EarthquakeInfoInMemoryDatabaseAdapter implements LoadEarthquakeInfoPort {
    private Map<LocalDate, List<String>> earthquakes;

    EarthquakeInfoInMemoryDatabaseAdapter() {
        this.earthquakes = new HashMap<>();
    }

    @Override
    public List<String> getInfoBetweenDates(String startDate, String endDate) {
        LocalDate sDate= parse(startDate);
        LocalDate eDate= parse(endDate).plusDays(1);

        List<String> earthquakeInfoInDateRange = new ArrayList<>();

        for (LocalDate date : getDateRange(sDate, eDate)){
            if(earthquakes.containsKey(date))
                earthquakeInfoInDateRange.addAll(earthquakes.get(date));
        }
        Collections.sort(earthquakeInfoInDateRange);
        return earthquakeInfoInDateRange;
    }

    private Set<LocalDate> getDateRange(LocalDate sDate, LocalDate eDate) {
        return sDate.datesUntil(eDate).collect(Collectors.toSet());
    }

    public void addEarthquakeInfo(String date, List<String> earthquakesInfo){
        LocalDate parsedDate = parse(date);
        if(earthquakes.containsKey(parsedDate))
            earthquakes.get(parsedDate).addAll(earthquakesInfo);
        else
            earthquakes.put(parsedDate, new ArrayList<>(earthquakesInfo));
    }
}
