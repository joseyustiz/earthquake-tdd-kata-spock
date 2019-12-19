package com.joseyustiz.earthquakeinfo.application.service;

import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase;
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;
import com.joseyustiz.earthquakeinfo.model.DateRange;
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class GetEarthquakeInfoService implements GetEarthquakeInfoUseCase {
    private LoadEarthquakeInfoPort loadEarthquakeInfoPort;

    public GetEarthquakeInfoService(LoadEarthquakeInfoPort loadEarthquakeInfoPort) {
        this.loadEarthquakeInfoPort = loadEarthquakeInfoPort;
    }

    public List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate) {
        return loadEarthquakeInfoPort.getInfoBetweenDates(startDate, endDate);
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(BigDecimal minMagnitude, BigDecimal maxMagnitude) {
        return loadEarthquakeInfoPort.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude);
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenTwoDateRanges(LocalDate startDateRange1, LocalDate endDateRange1, LocalDate startDateRange2, LocalDate endDateRange2) {
        List<DateRange> dateRanges = DateRange.getOptimumDateRange(new DateRange(startDateRange1, endDateRange1), new DateRange(startDateRange2, endDateRange2));
        Set<EarthquakeInfo> earthquakeInfoSet = new HashSet<>();
        for (DateRange dateRange : dateRanges) {
            earthquakeInfoSet.addAll(this.getInfoBetweenDates(dateRange.getStartDate(), dateRange.getEndDate()));
        }
        return earthquakeInfoSet.stream().sorted(comparing(EarthquakeInfo::getDate)).collect(toList());
    }

    @Override
    public List<EarthquakeInfo> getInfoByCountry(String country) {
        return loadEarthquakeInfoPort.getAllEarthquakesInfo().parallelStream()
                .filter(e -> e.getCountry().equals(country))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public int getAmountAtTwoCountriesNamesAndBetweenDates(String country1, String country2, LocalDate startDate, LocalDate endDate) {
        return getInfoByTwoCountriesNamesAndBetweenDates(country1, country2, startDate, endDate).size();
    }

    private List<EarthquakeInfo> getInfoByTwoCountriesNamesAndBetweenDates(String country1, String country2, LocalDate startDate, LocalDate endDate) {
        return loadEarthquakeInfoPort.getInfoBetweenDates(startDate, endDate)
                .parallelStream().filter(e -> e.getCountry().equals(country1) || e.getCountry().equals(country2))
                .collect(Collectors.toUnmodifiableList());
    }
}
