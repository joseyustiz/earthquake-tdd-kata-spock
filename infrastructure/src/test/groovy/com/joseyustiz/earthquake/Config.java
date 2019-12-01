package com.joseyustiz.earthquake;

import com.joseyustiz.earthquakeinfo.adapter.repository.EarthquakeInfoInMemoryDatabaseAdapter;
import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase;
import com.joseyustiz.earthquakeinfo.application.service.GetEarthquakeInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public GetEarthquakeInfoUseCase getGetEarthquakeInfoUseCase(){
        return new GetEarthquakeInfoService(new EarthquakeInfoInMemoryDatabaseAdapter());
    }
}
