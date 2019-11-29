package com.joseyustiz.earthquake;

import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDate.parse;

public class EarthquakeInfoInMemoryDatabaseAdapter implements LoadEarthquakeInfoPort {
    private Map<LocalDate, List<String>> earthquakes;

    EarthquakeInfoInMemoryDatabaseAdapter() {
        this.earthquakes = new HashMap<>();
        earthquakes.put(parse("2019-10-14"), Collections.singletonList("Earthquake 1"));
        earthquakes.put(parse("2019-10-15"),Collections.singletonList("Earthquake 2"));
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
}
