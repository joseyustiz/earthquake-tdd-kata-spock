package com.joseyustiz.earthquakeinfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public boolean isOverLap(DateRange dateRange){
        return equals(dateRange) || (startDate.compareTo(dateRange.getStartDate()) >= 0 && endDate.compareTo(dateRange.getEndDate()) <= 0) || (startDate.compareTo(dateRange.getStartDate()) <= 0 && endDate.compareTo(dateRange.getEndDate()) >= 0);
    }

    public boolean isConsecutive(DateRange dateRange){
        return !isOverLap(dateRange) && (endDate.isEqual(dateRange.startDate.minusDays(1)) || dateRange.getEndDate().isEqual(startDate.minusDays(1)));
    }

    public static List<DateRange> getOptimumDateRange(DateRange dateRange1, DateRange dateRange2) {
        List<DateRange> optimumDateRanges = new ArrayList<>();

        if(dateRange1.equals(dateRange2)) {
            optimumDateRanges.add(dateRange1);
        } else {
            if (dateRange1.isConsecutive(dateRange2) || dateRange1.isOverLap(dateRange2)) {
                optimumDateRanges.add(new DateRange(getMinimumStartDate(dateRange1, dateRange2), getMaximumEndDate(dateRange1, dateRange2)));
            } else {
                optimumDateRanges.add(dateRange1);
                optimumDateRanges.add(dateRange2);
            }
        }

        return optimumDateRanges;
    }

    private static LocalDate getMaximumEndDate(DateRange dateRange1, DateRange dateRange2) {
        return dateRange1.getEndDate().compareTo(dateRange2.getEndDate()) >= 0 ? dateRange1.getEndDate() : dateRange2.getEndDate();
    }

    private static LocalDate getMinimumStartDate(DateRange dateRange1, DateRange dateRange2) {
        return dateRange1.getStartDate().compareTo(dateRange2.getStartDate()) <= 0 ? dateRange1.getStartDate() : dateRange2.getStartDate();
    }
}
