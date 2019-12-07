package com.joseyustiz.earthquakeinfo.adapter.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class UUGSEarthquakeInfoQueryResult {
    private List<Feature> features;

}
