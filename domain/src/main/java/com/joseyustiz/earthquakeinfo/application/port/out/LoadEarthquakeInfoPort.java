package com.joseyustiz.earthquakeinfo.application.port.out;

import java.util.List;

public interface LoadEarthquakeInfoPort{
    List<String> getInfoBetweenDates(String startDate, String endDate);

    List<String> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude);
}
