package com.joseyustiz.earthquakeinfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public boolean isOverlapped(DateRange dateRange) {
        return this.equals(dateRange) || this.isSubsetOf(dateRange) || dateRange.isSubsetOf(this);
    }

    private boolean isSubsetOf(DateRange dateRange) {
        return dateRange != null && startDate.compareTo(dateRange.getStartDate()) >= 0 && endDate.compareTo(dateRange.getEndDate()) <= 0;
    }

    public boolean isConsecutive(DateRange dateRange) {
        return !this.isOverlapped(dateRange) && (endDate.isEqual(dateRange.startDate.minusDays(1)) || dateRange.getEndDate().isEqual(startDate.minusDays(1)));
    }

    public static List<DateRange> getOptimumDateRange(@NonNull DateRange dateRange1, @NonNull DateRange dateRange2) {
        List<DateRange> optimumDateRanges = new ArrayList<>();

        if (dateRange1.equals(dateRange2)) {
            optimumDateRanges.add(dateRange1);
        } else if (dateRange1.isConsecutive(dateRange2) || dateRange1.isOverlapped(dateRange2)) {
            optimumDateRanges.add(new DateRange(min(dateRange1.getStartDate(), dateRange2.getEndDate()),
                    max(dateRange1.getEndDate(), dateRange2.getEndDate())));
        } else {
            optimumDateRanges.add(dateRange1);
            optimumDateRanges.add(dateRange2);
        }

        return optimumDateRanges;
    }

    private static LocalDate max(@NonNull LocalDate localDate1, @NonNull LocalDate localDate2) {
        return localDate1.compareTo(localDate2) >= 0 ? localDate1 : localDate2;
    }

    private static LocalDate min(@NonNull LocalDate localDate1, @NonNull LocalDate localDate2) {
        return localDate1.compareTo(localDate2) <= 0 ? localDate1 : localDate2;
    }
}
