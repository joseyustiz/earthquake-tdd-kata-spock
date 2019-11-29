package com.joseyustiz.earthquakeinfo.application.port.in;

import java.util.List;

public interface GetEarthquakeInfoUseCase {
    List<String> getInfoBetweenDates(String startDate, String endDate);

    List<String> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude);
}
