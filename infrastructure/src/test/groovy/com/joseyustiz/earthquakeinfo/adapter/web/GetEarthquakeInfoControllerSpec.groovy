package com.joseyustiz.earthquakeinfo.adapter.web

import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.time.LocalDate.parse

class GetEarthquakeInfoControllerSpec extends Specification {
    @Subject
    @Shared
    private GetEarthquakeInfoController controller
    @Shared
    private def mockedService
    @Shared
    private def earthquake1
    @Shared
    private def earthquake2
    @Shared
    private def earthquake3
    @Shared
    private def earthquake4


    void setup() {
        earthquake1 = new EarthquakeInfo("Earthquake 1", parse("2019-10-14"), 6.0, "Mexico")
        earthquake2 = new EarthquakeInfo("Earthquake 2", parse("2019-10-15"), 7.0, "Chile")

        earthquake3 = new EarthquakeInfo("Earthquake 3", parse("2019-10-16"), 8.0, "Japan")
        earthquake4 = new EarthquakeInfo("Earthquake 4", parse("2019-10-17"), 9.0, "Japan")

        mockedService = Mock(GetEarthquakeInfoUseCase)
        mockedService.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-13")) >> []
        mockedService.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-14")) >> [earthquake1]
        mockedService.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-15")) >> [earthquake1, earthquake2]
        mockedService.getInfoBetweenDates(parse("2019-10-15"), parse("2019-10-16")) >> [earthquake2, earthquake3]
        mockedService.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-16")) >> [earthquake1, earthquake2, earthquake3]

        mockedService.getInfoBetweenTwoDateRanges(parse("2019-10-13"), parse("2019-10-13"), parse("2019-10-13"), parse("2019-10-13")) >> []
        mockedService.getInfoBetweenTwoDateRanges(parse("2019-10-13"), parse("2019-10-14"), parse("2019-10-13"), parse("2019-10-14")) >> [earthquake1]
        mockedService.getInfoBetweenTwoDateRanges(parse("2019-10-13"), parse("2019-10-15"), parse("2019-10-13"), parse("2019-10-14")) >> [earthquake1, earthquake2]
        mockedService.getInfoBetweenTwoDateRanges(parse("2019-10-13"), parse("2019-10-14"), parse("2019-10-15"), parse("2019-10-16")) >> [earthquake1, earthquake2, earthquake3]

        mockedService.getInfoBetweenMagnitudes(1.5, 2.0) >> []
        mockedService.getInfoBetweenMagnitudes(6.5, 7.0) >> [earthquake2]
        mockedService.getInfoBetweenMagnitudes(6.0, 7.0) >> [earthquake1, earthquake2]

        controller = new GetEarthquakeInfoController(mockedService)
    }

    @Unroll("#message")
    def "get earthquake info from database service between two dates by calling the Controller"() {
        expect:
        controller.getInfoBetweenDates(parse(startDate), parse(endDate)) == earthquakesInfoWebResponse

        where:
        startDate    | endDate      | earthquakesInfoWebResponse
        "2019-10-13" | "2019-10-13" | []
        "2019-10-13" | "2019-10-14" | ["Earthquake 1"]
        "2019-10-13" | "2019-10-15" | ["Earthquake 1", "Earthquake 2"]
        message = "Controller returned " + earthquakesInfoWebResponse.size() + " earthquake(s) info given that happened " + earthquakesInfoWebResponse.size() + " from " + startDate + " to " + endDate
    }

    @Unroll("#message")
    def "get earthquake info between two magnitudes by calling the Controller"() {
        expect:
        controller.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude) == earthquakesInfoWebResponse

        where:
        minMagnitude | maxMagnitude | earthquakesInfoWebResponse
        1.5          | 2.0          | []
        6.5          | 7.0          | ["Earthquake 2"]
        6.0          | 7.0          | ["Earthquake 1", "Earthquake 2"]
        message = "Controller returned " + earthquakesInfoWebResponse.size() + " earthquake(s) info given that happened " + earthquakesInfoWebResponse.size() + " with magnitude between " + minMagnitude + " to " + maxMagnitude
    }

    @Unroll("#message")
    def "get earthquakes info that happened between two date ranges by calling the Controller"() {
        expect:
        controller.getInfoBetweenTwoDateRanges(parse(startDateRange1), parse(endDateRange1), parse(startDateRange2), parse(endDateRange2)) == earthquakesInfoWebResponse

        where:
        startDateRange1 | endDateRange1 | startDateRange2 | endDateRange2 | earthquakesInfoWebResponse
        "2019-10-13"    | "2019-10-13"  | "2019-10-13"    | "2019-10-13"  | []
        "2019-10-13"    | "2019-10-14"  | "2019-10-13"    | "2019-10-14"  | ["Earthquake 1"]
        "2019-10-13"    | "2019-10-15"  | "2019-10-13"    | "2019-10-14"  | ["Earthquake 1", "Earthquake 2"]
        "2019-10-13"    | "2019-10-14"  | "2019-10-15"    | "2019-10-16"  | ["Earthquake 1", "Earthquake 2", "Earthquake 3"]
        message = "Retrieved " + earthquakesInfoWebResponse.size() + " earthquake(s) info given that happened " + earthquakesInfoWebResponse.size() + " earthquake(s) between " + startDateRange1 + " to " + endDateRange1 + " and " + startDateRange2 + " to " + endDateRange2
    }

    @Unroll("#message")
    def "get earthquakes info by country name by calling the Controller"() {
        expect:
        controller.getInfoByCountry(country) == earthquakesInfoWebResponse

        where:
        country  | earthquakesInfoWebResponse
        "Aruba"  | []
        "Mexico" | ["Earthquake 1"]
        "Chile"  | ["Earthquake 2"]
        "Japan"  | ["Earthquake 3","Earthquake 4"]

        message = "Controller returned " + earthquakesInfoWebResponse.size() + " earthquake(s) info given that happened " + earthquakesInfoWebResponse.size() + " earthquake(s) at " + country
    }
}
