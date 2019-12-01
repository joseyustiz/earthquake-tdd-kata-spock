package com.joseyustiz.earthquakeinfo.adapter.web;

import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase;
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GetEarthquakeInfoController {
    private final GetEarthquakeInfoUseCase service;

    public List<String> getInfoBetweenDates(LocalDate startDate, LocalDate endDate) {
        return service.getInfoBetweenDates(startDate, endDate).stream().map(EarthquakeInfo::getInfo).collect(Collectors.toUnmodifiableList());
    }

    public List<String> getInfoBetweenMagnitud(double minMagnitude, double maxMagnitude) {
        return service.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude).stream().map(EarthquakeInfo::getInfo).collect(Collectors.toUnmodifiableList());
    }
}
