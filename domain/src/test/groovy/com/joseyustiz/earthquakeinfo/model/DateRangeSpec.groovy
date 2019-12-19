package com.joseyustiz.earthquakeinfo.model

import spock.lang.Shared
import spock.lang.Specification

import static java.time.LocalDate.parse

class DateRangeSpec extends Specification{
    @Shared DateRange dataRange20191013_20191013
    @Shared DateRange dataRange20191013_20191013_2
    @Shared DateRange dataRange20191013_20191014
    @Shared DateRange dataRange20191013_20191015
    @Shared DateRange dataRange20191013_20191016
    @Shared DateRange dataRange20191015_20191016
    @Shared DateRange dataRange20191010_20191016


    void setupSpec() {
        dataRange20191013_20191013 = new DateRange(parse("2019-10-13"),parse("2019-10-13"))
        dataRange20191013_20191013_2 = new DateRange(parse("2019-10-13"),parse("2019-10-13"))
        dataRange20191013_20191014 = new DateRange(parse("2019-10-13"),parse("2019-10-14"))
        dataRange20191013_20191015 = new DateRange(parse("2019-10-13"),parse("2019-10-15"))
        dataRange20191013_20191016 = new DateRange(parse("2019-10-13"),parse("2019-10-16"));
        dataRange20191015_20191016 = new DateRange(parse("2019-10-15"),parse("2019-10-16"))
        dataRange20191010_20191016 = new DateRange(parse("2019-10-10"),parse("2019-10-16"))

    }

    def "the ranges are overlap if the ranges share common dates"(){
        expect:
        dataRange20191013_20191013.isOverLap(dataRange20191013_20191013_2)
        dataRange20191013_20191013.isOverLap(dataRange20191013_20191014)
        dataRange20191013_20191013.isOverLap(dataRange20191013_20191015)
        dataRange20191013_20191013.isOverLap(dataRange20191010_20191016)
        dataRange20191010_20191016.isOverLap(dataRange20191013_20191015)
        !dataRange20191013_20191013.isOverLap(dataRange20191015_20191016)
    }

    def "the ranges are consecutive if the endDate ranges1 is a day before the startDate of range2 or vice versa"(){
        expect:
        dateRange1.isConsecutive(dateRange2) == result

        where:
        dateRange1 | dateRange2 | result
        dataRange20191013_20191013 |dataRange20191013_20191013 | false
        dataRange20191013_20191013 |dataRange20191013_20191014 | false
        dataRange20191013_20191013 |dataRange20191013_20191015 | false
        dataRange20191013_20191013 |dataRange20191010_20191016 | false
        dataRange20191013_20191014 |dataRange20191015_20191016 | true
        dataRange20191015_20191016 |dataRange20191013_20191014 | true
    }

    def "get optimum date range of two date ranges"(){
        expect:
        DateRange.getOptimumDateRange(dateRange1, dateRange2) == optimumDateRange

        where:
        dateRange1 | dateRange2 | optimumDateRange
        dataRange20191013_20191013 |dataRange20191013_20191013 | [dataRange20191013_20191013]
        dataRange20191013_20191013 |dataRange20191013_20191014 | [dataRange20191013_20191014]
        dataRange20191013_20191014 |dataRange20191015_20191016 | [dataRange20191013_20191016]
        dataRange20191010_20191016 |dataRange20191013_20191015 | [dataRange20191010_20191016]
        dataRange20191013_20191013 |dataRange20191015_20191016 | [dataRange20191013_20191013, dataRange20191015_20191016]
    }
}
