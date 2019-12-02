package com.joseyustiz.earthquakeinfo.adapter.web;

import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase;
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GetEarthquakeInfoController {
    private final GetEarthquakeInfoUseCase service;

    public List<String> getInfoBetweenDates(LocalDate startDate, LocalDate endDate) {
        return mapInfoTo(service.getInfoBetweenDates(startDate, endDate));
    }

    public List<String> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude) {
        return mapInfoTo(service.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude));
    }

    public List<String> getInfoBetweenTwoDateRanges(LocalDate startDateRange1, LocalDate endDateRange1, LocalDate startDateRange2, LocalDate endDateRange2){
        return mapInfoTo(service.getInfoBetweenTwoDateRanges(startDateRange1, endDateRange1, startDateRange2, endDateRange2));
    }

    public List<String> getInfoByCountry(String country) {
        return mapInfoTo(service.getInfoByCountry(country));
    }

    private List<String> mapInfoTo(List<EarthquakeInfo> infoBetweenTwoDateRanges) {
        return infoBetweenTwoDateRanges.stream().map(EarthquakeInfo::getInfo).collect(Collectors.toUnmodifiableList());
    }

    public int getAmountAtTwoCountriesNamesAndBetweenDates(String country1, String country2, LocalDate startDate, LocalDate endDate) {
        return service.getAmountAtTwoCountriesNamesAndBetweenDates(country1, country2, startDate,endDate);
    }
}
