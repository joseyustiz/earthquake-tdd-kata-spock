package com.joseyustiz.earthquakeinfo.application.service;

import com.joseyustiz.earthquakeinfo.EarthquakeInfo;
import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase;
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;

import java.util.List;

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
}
