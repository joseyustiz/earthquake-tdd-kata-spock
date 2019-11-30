package com.joseyustiz.earthquakeinfo.application.port.out;

import com.joseyustiz.earthquakeinfo.EarthquakeInfo;

import java.time.LocalDate;
import java.util.List;

public interface LoadEarthquakeInfoPort{
    List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate);

    List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude);
}
