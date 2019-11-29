package com.joseyustiz.earthquakeinfo.adapter.repository;

import com.joseyustiz.earthquakeinfo.EarthquakeInfo;
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.LocalDate.parse;
import static java.util.Comparator.comparing;

public class EarthquakeInfoInMemoryDatabaseAdapter implements LoadEarthquakeInfoPort {
    private Set<EarthquakeInfo> earthquakes;

    EarthquakeInfoInMemoryDatabaseAdapter() {
        this.earthquakes = new HashSet<>();
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenDates(String startDate, String endDate) {
        LocalDate sDate = parse(startDate);
        LocalDate eDate = parse(endDate).plusDays(1);
        return earthquakes.stream()
                .filter(i -> i.getDate().isAfter(sDate) && i.getDate().isBefore(eDate))
                .sorted(comparing(EarthquakeInfo::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude) {
        return earthquakes.stream()
                .filter(i -> i.getMagnitude() >= minMagnitude && i.getMagnitude() <= maxMagnitude)
                .sorted(comparing(EarthquakeInfo::getDate))
                .collect(Collectors.toList());
    }

    public void addEarthquakeInfo(EarthquakeInfo earthquakesInfo) {
        earthquakes.add(earthquakesInfo);
    }
}
