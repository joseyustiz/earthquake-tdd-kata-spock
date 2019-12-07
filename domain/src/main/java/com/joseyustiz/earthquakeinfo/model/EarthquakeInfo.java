package com.joseyustiz.earthquakeinfo.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EarthquakeInfo {
    private String info;
    private LocalDate date;
    private BigDecimal magnitude;
    private String country;
}
