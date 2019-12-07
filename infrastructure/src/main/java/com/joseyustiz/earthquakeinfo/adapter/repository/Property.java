package com.joseyustiz.earthquakeinfo.adapter.repository;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
class Property {
    private BigDecimal mag;
    private String place;
    private long time;
    private long updated;
    private int tz;
    private String url;
    private String detail;
    private String felt;
    private String cdi;
    private String mmi;
    private String alert;
    private String status;
    private int tsunami;
    private int sig;
    private String net;
    private String code;
    private String ids;
    private String sources;
    private String types;
    private int nst;
    private BigDecimal dmin;
    private BigDecimal rms;
    private int gap;
    private String magType;
    private String type;
    private String title;
}
