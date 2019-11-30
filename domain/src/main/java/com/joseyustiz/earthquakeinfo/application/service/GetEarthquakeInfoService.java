package com.joseyustiz.earthquakeinfo.application.service;

import com.joseyustiz.earthquakeinfo.EarthquakeInfo;
import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase;
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.LocalDate.parse;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class GetEarthquakeInfoService implements GetEarthquakeInfoUseCase {
    private LoadEarthquakeInfoPort loadEarthquakeInfoPort;

    public GetEarthquakeInfoService(LoadEarthquakeInfoPort loadEarthquakeInfoPort) {
        this.loadEarthquakeInfoPort = loadEarthquakeInfoPort;
    }

    public List<EarthquakeInfo> getInfoBetweenDates(String startDate, String endDate) {
        return loadEarthquakeInfoPort.getInfoBetweenDates(startDate,endDate);
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude) {
        return loadEarthquakeInfoPort.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude);
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenTwoDateRanges(String startDateRange1, String endDateRange1, String startDateRange2, String endDateRange2) {
        List<DataRange> dataRanges= getOptimumDateRange(parse(startDateRange1), parse(endDateRange1),parse(startDateRange2), parse(endDateRange2));
        Set<EarthquakeInfo> earthquakeInfoSet = new HashSet<>();
        for (DataRange dataRange : dataRanges){
            earthquakeInfoSet.addAll(this.getInfoBetweenDates(dataRange.getStartDate(),dataRange.getEndDate()));
        }
        return earthquakeInfoSet.stream().sorted(comparing(EarthquakeInfo::getDate)).collect(toList());
    }

    private List<DataRange> getOptimumDateRange(LocalDate startDateRange1, LocalDate endDateRange1, LocalDate startDateRange2, LocalDate endDateRange2) {
        List<DataRange> optimumDateRanges = new ArrayList<>();
        DataRange dataRange;
        if(startDateRange1.isEqual(startDateRange2)){
            dataRange = new DataRange();
            dataRange.startDate = startDateRange1.toString();
            if (endDateRange1.isEqual(endDateRange2) || endDateRange1.isAfter(endDateRange2) ){
                dataRange.endDate = endDateRange1.toString();
            } else {
                dataRange.endDate = endDateRange2.toString();
            }
            optimumDateRanges.add(dataRange);
        } else if(startDateRange1.isBefore(startDateRange2)){
            dataRange = new DataRange();
            dataRange.startDate = startDateRange1.toString();
            if(endDateRange1.isEqual(startDateRange2.minusDays(1)) || endDateRange1.isAfter(startDateRange2)){
                dataRange.endDate = endDateRange2.toString();
                optimumDateRanges.add(dataRange);
            }else{
                dataRange.endDate = endDateRange1.toString();
                optimumDateRanges.add(dataRange);
                DataRange dataRange2 = new DataRange();
                dataRange2.startDate=startDateRange2.toString();
                dataRange2.endDate=endDateRange2.toString();
                optimumDateRanges.add(dataRange2);
            }

        }
        return optimumDateRanges;
    }

    @Getter
    private class DataRange {
        String startDate;
        String endDate;
    }
}
