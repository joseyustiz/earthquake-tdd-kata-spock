package com.joseyustiz.earthquakeinfo.adapter.web;

import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase;
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetEarthquakeInfoController {
    private final GetEarthquakeInfoUseCase service;

    public List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate) {
        return service.getInfoBetweenDates(startDate,endDate);
    }
}
