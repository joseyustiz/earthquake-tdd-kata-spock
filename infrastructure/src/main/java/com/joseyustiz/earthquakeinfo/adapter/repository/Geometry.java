package com.joseyustiz.earthquakeinfo.adapter.repository;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
class Geometry {
    private String type;
    private List<BigDecimal> coordinates;
}
