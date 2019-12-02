package com.joseyustiz.earthquakeinfo.adapter.repository;

import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

public class USGSEarthquakeServiceAdapter implements LoadEarthquakeInfoPort {
    private RestTemplate restTemplate;

    public USGSEarthquakeServiceAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude) {
        return null;
    }

    @Override
    public List<EarthquakeInfo> getAllEarthquakesInfo() {
        return null;
    }
}
