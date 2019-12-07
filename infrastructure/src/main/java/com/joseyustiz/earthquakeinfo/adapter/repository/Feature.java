package com.joseyustiz.earthquakeinfo.adapter.repository;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Feature {
    private String type;
    private Property properties;
    private Geometry geometry;
    private String id;

}
