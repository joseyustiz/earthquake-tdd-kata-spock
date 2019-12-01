package com.joseyustiz.earthquakeinfo.application.service;

import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase;
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort;
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class GetEarthquakeInfoService implements GetEarthquakeInfoUseCase {
    private LoadEarthquakeInfoPort loadEarthquakeInfoPort;

    public GetEarthquakeInfoService(LoadEarthquakeInfoPort loadEarthquakeInfoPort) {
        this.loadEarthquakeInfoPort = loadEarthquakeInfoPort;
    }

    public List<EarthquakeInfo> getInfoBetweenDates(LocalDate startDate, LocalDate endDate) {
        return loadEarthquakeInfoPort.getInfoBetweenDates(startDate,endDate);
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenMagnitudes(double minMagnitude, double maxMagnitude) {
        return loadEarthquakeInfoPort.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude);
    }

    @Override
    public List<EarthquakeInfo> getInfoBetweenTwoDateRanges(LocalDate startDateRange1, LocalDate endDateRange1, LocalDate startDateRange2, LocalDate endDateRange2) {
        List<DataRange> dataRanges= getOptimumDateRange(startDateRange1, endDateRange1, startDateRange2, endDateRange2);
        Set<EarthquakeInfo> earthquakeInfoSet = new HashSet<>();
        for (DataRange dataRange : dataRanges){
            earthquakeInfoSet.addAll(this.getInfoBetweenDates(dataRange.getStartDate(),dataRange.getEndDate()));
        }
        return earthquakeInfoSet.stream().sorted(comparing(EarthquakeInfo::getDate)).collect(toList());
    }

    @Override
    public List<EarthquakeInfo> getInfoByCountry(String country) {
        return null;
    }

    private List<DataRange> getOptimumDateRange(LocalDate startDateRange1, LocalDate endDateRange1, LocalDate startDateRange2, LocalDate endDateRange2) {
        List<DataRange> optimumDateRanges = new ArrayList<>();
        DataRange dataRange;
        if(startDateRange1.isEqual(startDateRange2)){
            dataRange = new DataRange();
            dataRange.startDate = startDateRange1;
            if (endDateRange1.isEqual(endDateRange2) || endDateRange1.isAfter(endDateRange2) ){
                dataRange.endDate = endDateRange1;
            } else {
                dataRange.endDate = endDateRange2;
            }
            optimumDateRanges.add(dataRange);
        } else if(startDateRange1.isBefore(startDateRange2)){
            dataRange = new DataRange();
            dataRange.startDate = startDateRange1;
            if(endDateRange1.isEqual(startDateRange2.minusDays(1)) || endDateRange1.isAfter(startDateRange2)){
                dataRange.endDate = endDateRange2;
                optimumDateRanges.add(dataRange);
            }else{
                dataRange.endDate = endDateRange1;
                optimumDateRanges.add(dataRange);
                DataRange dataRange2 = new DataRange();
                dataRange2.startDate=startDateRange2;
                dataRange2.endDate=endDateRange2;
                optimumDateRanges.add(dataRange2);
            }

        }
        return optimumDateRanges;
    }

    @Getter
    private class DataRange {
        LocalDate startDate;
        LocalDate endDate;
    }
}
