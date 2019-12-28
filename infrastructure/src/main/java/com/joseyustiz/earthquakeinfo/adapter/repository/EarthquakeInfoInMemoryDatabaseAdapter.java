package com.joseyustiz.earthquakeinfo.adapter.repository;

import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class EarthquakeInfoInMemoryDatabaseAdapter implements LoadEarthquakeInfoPort {
    private final Set<EarthquakeInfo> earthquakes;

    public EarthquakeInfoInMemoryDatabaseAdapter() {
        this.earthquakes = new HashSet<>();
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate) {
        LocalDate eDate = endDate.plusDays(1);
        return earthquakes.stream()
                .filter(i -> i.getDate().isAfter(startDate) && i.getDate().isBefore(eDate))
                .sorted(comparing(EarthquakeInfo::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(BigDecimal minMagnitude, BigDecimal maxMagnitude) {
        return earthquakes.stream()
                .filter(i -> i.getMagnitude().compareTo(minMagnitude) >= 0 && i.getMagnitude().compareTo(maxMagnitude) <= 0)
                .sorted(comparing(EarthquakeInfo::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<EarthquakeInfo> getAllEarthquakesInfo() {
        return null;
    }

    public void addEarthquakeInfo(EarthquakeInfo earthquakesInfo) {
        earthquakes.add(earthquakesInfo);
    }
}
