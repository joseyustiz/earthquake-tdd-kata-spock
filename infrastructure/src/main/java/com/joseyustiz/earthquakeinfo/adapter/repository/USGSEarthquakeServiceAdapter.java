package com.joseyustiz.earthquakeinfo.adapter.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class USGSEarthquakeServiceAdapter implements LoadEarthquakeInfoPort {
    private RestTemplate restTemplate;
    private URL serviceUrl;

    public USGSEarthquakeServiceAdapter(RestTemplate restTemplate, URL serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate) {
        String url = serviceUrl.toString() + "&starttime=" + startDate + "&endtime=" + endDate;
        UUGSEarthquakeInfoQueryResult serviceResponse = restTemplate.getForObject(url, UUGSEarthquakeInfoQueryResult.class);
        ObjectMapper objectMapper = new ObjectMapper();
        assert serviceResponse != null;
        return serviceResponse.getFeatures().stream()
                .map(e -> {
                    try {
                        return EarthquakeInfo.builder()
                                .magnitude(e.getProperties().getMag())
                                .country(getCountry(e.getProperties().getPlace()))
                                .date(getLocalDate(e.getProperties().getTime()))
                                .info(objectMapper.writeValueAsString(e))
                                .build();
                    } catch (JsonProcessingException ex) {
                        log.error("Problem converting USGS Earthquake Feature to json", ex);
                    }
                    return new EarthquakeInfo();
                })
                .collect(Collectors.toUnmodifiableList());
    }

    private LocalDate getLocalDate(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String getCountry(String place) {
        String[] token = place.split(",");
        return token.length > 0 ? token[token.length-1].trim(): "";
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(BigDecimal minMagnitude, BigDecimal maxMagnitude) {
        String url = serviceUrl.toString() + "&minmagnitude=" + minMagnitude + "&maxmagnitude=" + maxMagnitude;
        UUGSEarthquakeInfoQueryResult serviceResponse = restTemplate.getForObject(url, UUGSEarthquakeInfoQueryResult.class);
        ObjectMapper objectMapper = new ObjectMapper();
        assert serviceResponse != null;
        return serviceResponse.getFeatures().stream()
                .map(e -> {
                    try {
                        return EarthquakeInfo.builder()
                                .magnitude(e.getProperties().getMag())
                                .country(getCountry(e.getProperties().getPlace()))
                                .date(getLocalDate(e.getProperties().getTime()))
                                .info(objectMapper.writeValueAsString(e))
                                .build();
                    } catch (JsonProcessingException ex) {
                        log.error("Problem converting USGS Earthquake Feature to json", ex);
                    }
                    return new EarthquakeInfo();
                })
                .collect(Collectors.toUnmodifiableList());    }

    @Override
    public List<EarthquakeInfo> getAllEarthquakesInfo() {
        return null;
    }
}
