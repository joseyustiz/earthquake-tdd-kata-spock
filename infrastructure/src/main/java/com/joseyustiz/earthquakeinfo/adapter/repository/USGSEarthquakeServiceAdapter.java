package com.joseyustiz.earthquakeinfo.adapter.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class USGSEarthquakeServiceAdapter implements LoadEarthquakeInfoPort {
    private final RestTemplate restTemplate;
    private final URL serviceUrl;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate) {
        String url = serviceUrl.toString() + "&starttime=" + startDate + "&endtime=" + endDate;
        return getEarthquakesInfoFromWebService(url);
    }

    private List<EarthquakeInfo> getEarthquakesInfoFromWebService(String url) {
        UUGSEarthquakeInfoQueryResult serviceResponse = restTemplate.getForObject(url, UUGSEarthquakeInfoQueryResult.class);
        assert serviceResponse != null;
        return serviceResponse.getFeatures().stream()
                .map(e -> mapEarthquakeInfoFor(e))
                .collect(Collectors.toUnmodifiableList());
    }

    private EarthquakeInfo mapEarthquakeInfoFor(Feature e) {
        try {
            return EarthquakeInfo.builder()
                    .magnitude(e.getProperties().getMag())
                    .country(exytractCountry(e.getProperties().getPlace()))
                    .date(getLocalDateForEpochMilli(e.getProperties().getTime()))
                    .info(OBJECT_MAPPER.writeValueAsString(e))
                    .build();
        } catch (JsonProcessingException ex) {
            log.error("Problem converting USGS Earthquake Feature to json", ex);
        }
        return new EarthquakeInfo();
    }

    private LocalDate getLocalDateForEpochMilli(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String exytractCountry(String place) {
        String[] token = place.split(",");
        return token.length > 0 ? token[token.length - 1].trim() : "";
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(BigDecimal minMagnitude, BigDecimal maxMagnitude) {
        String url = serviceUrl.toString() + "&minmagnitude=" + minMagnitude + "&maxmagnitude=" + maxMagnitude;
        return getEarthquakesInfoFromWebService(url);
    }

    @Override
    public List<EarthquakeInfo> getAllEarthquakesInfo() {
        String url = serviceUrl.toString();
        return getEarthquakesInfoFromWebService(url);
    }
}
