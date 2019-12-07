package com.joseyustiz.earthquakeinfo.application.port.in;

import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GetEarthquakeInfoUseCase {
    List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate);

    List<EarthquakeInfo> getInfoBetweenMagnitudes(BigDecimal minMagnitude, BigDecimal maxMagnitude);

    List<EarthquakeInfo> getInfoBetweenTwoDateRanges(LocalDate startDateRange1, LocalDate endDateRange1, LocalDate startDateRange2, LocalDate endDateRange2);

    List<EarthquakeInfo> getInfoByCountry(String country);

    int getAmountAtTwoCountriesNamesAndBetweenDates(String country1, String country2, LocalDate startDate, LocalDate endDate);

}
