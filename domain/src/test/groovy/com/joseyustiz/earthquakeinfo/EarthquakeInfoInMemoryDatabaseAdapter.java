package com.joseyustiz.earthquakeinfo;

import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDate.parse;
import static java.util.Comparator.comparing;

public class EarthquakeInfoInMemoryDatabaseAdapter implements LoadEarthquakeInfoPort {
    private Map<LocalDate, List<EarthquakeInfo>> earthquakes;

    EarthquakeInfoInMemoryDatabaseAdapter() {
        this.earthquakes = new HashMap<>();
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenDates(String startDate, String endDate) {
        LocalDate sDate= parse(startDate);
        LocalDate eDate= parse(endDate).plusDays(1);

        List<EarthquakeInfo> earthquakeInfoInDateRange = new ArrayList<>();

        for (LocalDate date : getDateRange(sDate, eDate)){
            if(earthquakes.containsKey(date))
                earthquakeInfoInDateRange.addAll(earthquakes.get(date));
        }
        earthquakeInfoInDateRange.sort(comparing(EarthquakeInfo::getDate));
        return earthquakeInfoInDateRange;
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude) {
        return null;
    }

    private Set<LocalDate> getDateRange(LocalDate sDate, LocalDate eDate) {
        return sDate.datesUntil(eDate).collect(Collectors.toSet());
    }

    public void addEarthquakeInfo(LocalDate date, List<EarthquakeInfo> earthquakesInfo){
        if(earthquakes.containsKey(date))
            earthquakes.get(date).addAll(earthquakesInfo);
        else
            earthquakes.put(date, new ArrayList<>(earthquakesInfo));
    }
}
