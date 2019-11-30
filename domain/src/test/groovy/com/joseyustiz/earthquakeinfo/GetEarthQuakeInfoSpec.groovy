package com.joseyustiz.earthquakeinfo

import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort
import com.joseyustiz.earthquakeinfo.application.service.GetEarthquakeInfoService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.time.LocalDate.parse

class GetEarthquakeInfoSpec extends Specification {
    @Subject
    @Shared
    private GetEarthquakeInfoUseCase earthquakeInfoService;
    @Shared
    private def earthquake1
    @Shared
    private def earthquake2
    @Shared
    private def earthquake3
    @Shared
    private def earthquake4
    @Shared
    private def mockedEarthquakeDatabase = Mock(LoadEarthquakeInfoPort)

    def setupSpec() {
        earthquake1 = new EarthquakeInfo("Earthquake 1", parse("2019-10-14"), 6.0)
        earthquake2 = new EarthquakeInfo("Earthquake 2", parse("2019-10-15"), 7.0)

        earthquake3 = new EarthquakeInfo("Earthquake 3", parse("2019-10-16"), 8.0)
        earthquake4 = new EarthquakeInfo("Earthquake 4", parse("2019-10-17"), 9.0)

        mockedEarthquakeDatabase.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-13")) >> []
        mockedEarthquakeDatabase.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-14")) >> [earthquake1]
        mockedEarthquakeDatabase.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-15")) >> [earthquake1, earthquake2]
        mockedEarthquakeDatabase.getInfoBetweenDates(parse("2019-10-15"), parse("2019-10-16")) >> [earthquake2, earthquake3]
        mockedEarthquakeDatabase.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-16")) >> [earthquake1, earthquake2, earthquake3]

        mockedEarthquakeDatabase.getInfoBetweenMagnitudes(1.5, 2.0) >> []
        mockedEarthquakeDatabase.getInfoBetweenMagnitudes(6.5, 7.0) >> [earthquake2]
        mockedEarthquakeDatabase.getInfoBetweenMagnitudes(6.0, 7.0) >> [earthquake1, earthquake2]

        earthquakeInfoService = new GetEarthquakeInfoService(mockedEarthquakeDatabase)
    }

    @Unroll("#message")
    def "get earthquake info from database service between two dates"() {
        expect:
        earthquakeInfoService.getInfoBetweenDates(parse(startDate), parse(endDate)) == earthquakesInfo

        where:
        startDate    | endDate      | earthquakesInfo
        "2019-10-13" | "2019-10-13" | []
        "2019-10-13" | "2019-10-14" | [earthquake1]
        "2019-10-13" | "2019-10-15" | [earthquake1, earthquake2]

        message = "Retrieved " + earthquakesInfo.size() + " earthquake(s) info given that happened " + earthquakesInfo.size() + " from " + startDate + " to " + endDate
    }

    @Unroll("#message")
    def "get earthquake info between two magnitudes from database service"() {
        expect:
        earthquakeInfoService.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude) == earthquakesInfo

        where:
        minMagnitude | maxMagnitude | earthquakesInfo
        1.5          | 2.0          | []
        6.5          | 7.0          | [earthquake2]
        6.0          | 7.0          | [earthquake1, earthquake2]
        message = "Retrieved " + earthquakesInfo.size() + " earthquake(s) info given that happened " + earthquakesInfo.size() + " with magnitude between " + minMagnitude + " to " + maxMagnitude
    }

    @Unroll("#message")
    def "get earthquakes info that happened between two date ranges"() {
        expect:
        earthquakeInfoService.getInfoBetweenTwoDateRanges(parse(startDateRange1), parse(endDateRange1), parse(startDateRange2), parse(endDateRange2)) == earthquakesInfo

        where:
        startDateRange1 | endDateRange1 | startDateRange2 | endDateRange2 | earthquakesInfo
        "2019-10-13"    | "2019-10-13"  | "2019-10-13"    | "2019-10-13"  | []
        "2019-10-13"    | "2019-10-14"  | "2019-10-13"    | "2019-10-14"  | [earthquake1]
        "2019-10-13"    | "2019-10-15"  | "2019-10-13"    | "2019-10-14"  | [earthquake1, earthquake2]
        "2019-10-13"    | "2019-10-14"  | "2019-10-15"    | "2019-10-16"  | [earthquake1, earthquake2, earthquake3]
        message = "Retrieved " + earthquakesInfo.size() + " earthquake(s) info given that happened " + earthquakesInfo.size() + " earthquake(s) between " + startDateRange1 + " to " + endDateRange1 + " and " + startDateRange2 + " to " + endDateRange2
    }

    def "the date ranges are optimized before requesting earthquakes info"() {
        given:
        LoadEarthquakeInfoPort mockedEarthquakeDatabase2 = Mock(LoadEarthquakeInfoPort)
        GetEarthquakeInfoService earthquakeInfoService2 = new GetEarthquakeInfoService(mockedEarthquakeDatabase2)

        when:
        earthquakeInfoService2.getInfoBetweenTwoDateRanges(parse(startDateRange1), parse(endDateRange1), parse(startDateRange2), parse(endDateRange2))

        then:
        callTimes13_13 * mockedEarthquakeDatabase2.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-13")) >> []
        callTimes13_14 * mockedEarthquakeDatabase2.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-14")) >> [earthquake1]
        callTimes13_15 * mockedEarthquakeDatabase2.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-15")) >> [earthquake1, earthquake2]
        callTimes13_16 * mockedEarthquakeDatabase2.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-16")) >> [earthquake1, earthquake2, earthquake3]
        callTimes16_17 * mockedEarthquakeDatabase2.getInfoBetweenDates(parse("2019-10-16"), parse("2019-10-17")) >> [ earthquake3, earthquake4]

        where:
        startDateRange1 | endDateRange1 | startDateRange2 | endDateRange2 | callTimes13_13 | callTimes13_14 | callTimes13_15 | callTimes13_16 | callTimes16_17
        "2019-10-13"    | "2019-10-13"  | "2019-10-13"    | "2019-10-13"  | 1              | 0              | 0              | 0              | 0
        "2019-10-13"    | "2019-10-14"  | "2019-10-13"    | "2019-10-14"  | 0              | 1              | 0              | 0              | 0
        "2019-10-13"    | "2019-10-15"  | "2019-10-13"    | "2019-10-14"  | 0              | 0              | 1              | 0              | 0
        "2019-10-13"    | "2019-10-14"  | "2019-10-15"    | "2019-10-16"  | 0              | 0              | 0              | 1              | 0
        "2019-10-13"    | "2019-10-14"  | "2019-10-16"    | "2019-10-17"  | 0              | 1              | 0              | 0              | 1
        "2019-10-13"    | "2019-10-13"  | "2019-10-16"    | "2019-10-17"  | 1              | 0              | 0              | 0              | 1
    }
}