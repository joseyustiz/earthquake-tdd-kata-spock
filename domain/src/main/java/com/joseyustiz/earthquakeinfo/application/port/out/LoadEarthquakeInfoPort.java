package com.joseyustiz.earthquakeinfo.application.port.out;

import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface LoadEarthquakeInfoPort{
    List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate);

    List<EarthquakeInfo> getInfoBetweenMagnitudes(BigDecimal minMagnitude, BigDecimal maxMagnitude);

    List<EarthquakeInfo> getAllEarthquakesInfo();
}
