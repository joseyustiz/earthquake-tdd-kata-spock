package com.joseyustiz.earthquakeinfo.application.port.in;

import com.joseyustiz.earthquakeinfo.EarthquakeInfo;

import java.util.List;

public interface GetEarthquakeInfoUseCase {
    List<EarthquakeInfo> getInfoBetweenDates(String startDate, String endDate);

    List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude);

    List<EarthquakeInfo> getInfoBetweenTwoDateRanges(String startDateRange1, String endDateRange1, String startDateRange2, String endDateRange2);
}
