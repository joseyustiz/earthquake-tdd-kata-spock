package com.joseyustiz.earthquakeinfo.application.port.out;

import com.joseyustiz.earthquakeinfo.EarthquakeInfo;

import java.util.List;

public interface LoadEarthquakeInfoPort{
    List<EarthquakeInfo> getInfoBetweenDates(String startDate, String endDate);

    List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude);
}
