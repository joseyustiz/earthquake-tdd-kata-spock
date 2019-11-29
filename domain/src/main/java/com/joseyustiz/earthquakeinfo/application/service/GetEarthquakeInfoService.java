package com.joseyustiz.earthquakeinfo.application.service;

import com.joseyustiz.earthquakeinfo.EarthquakeInfo;
import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase;
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class GetEarthquakeInfoService implements GetEarthquakeInfoUseCase {
    private LoadEarthquakeInfoPort loadEarthquakeInfoPort;

    public GetEarthquakeInfoService(LoadEarthquakeInfoPort loadEarthquakeInfoPort) {
        this.loadEarthquakeInfoPort = loadEarthquakeInfoPort;
    }

    public List<EarthquakeInfo> getInfoBetweenDates(String startDate, String endDate) {
        return loadEarthquakeInfoPort.getInfoBetweenDates(startDate,endDate);
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude) {
        return loadEarthquakeInfoPort.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude);
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenTwoDateRanges(String startDateRange1, String endDateRange1, String startDateRange2, String endDateRange2) {
        Set<EarthquakeInfo> infoBetweenDatesR1 = new HashSet<>(loadEarthquakeInfoPort.getInfoBetweenDates(startDateRange1, endDateRange1));
        List<EarthquakeInfo> infoBetweenDates = loadEarthquakeInfoPort.getInfoBetweenDates(startDateRange2, endDateRange2);
        infoBetweenDatesR1.addAll(infoBetweenDates);
        return infoBetweenDatesR1.stream().sorted(comparing(EarthquakeInfo::getDate)).collect(toList());
    }
}
