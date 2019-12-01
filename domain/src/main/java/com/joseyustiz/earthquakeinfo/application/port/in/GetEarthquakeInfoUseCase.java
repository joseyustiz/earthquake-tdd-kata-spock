package com.joseyustiz.earthquakeinfo.application.port.in;

import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;

import java.time.LocalDate;
import java.util.List;

public interface GetEarthquakeInfoUseCase {
    List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate);

    List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude);

    List<EarthquakeInfo> getInfoBetweenTwoDateRanges(LocalDate startDateRange1, LocalDate endDateRange1, LocalDate startDateRange2, LocalDate endDateRange2);
}
