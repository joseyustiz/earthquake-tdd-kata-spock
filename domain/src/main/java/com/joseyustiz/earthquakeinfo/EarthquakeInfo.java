package com.joseyustiz.earthquakeinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class EarthquakeInfo {
    private String info;
    private LocalDate date;
    private double magnitude;
}
