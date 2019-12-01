package com.joseyustiz.earthquakeinfo.adapter.web


import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo
import spock.lang.Shared
import spock.lang.Specification

import static java.time.LocalDate.parse

class GetEarthquakeInfoControllerSpec extends Specification {

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


    }

    def "get earthquake info from database service between two dates by calling REST API"() {
        given:
        def service = Mock(GetEarthquakeInfoUseCase)
        service.getInfoBetweenDates(parse(startDate),parse(endDate)) >> earthquakesInfo
        def controller = new GetEarthquakeInfoController(service)

        expect:
        controller.getInfoBetweenDates(parse(startDate),parse(endDate)) == earthquakesInfo
        where:
        startDate    | endDate      | earthquakesInfo
        "2019-10-13" | "2019-10-13" | []
        "2019-10-13" | "2019-10-14" | [earthquake1]
        "2019-10-13" | "2019-10-15" | [earthquake1, earthquake2]
    }
}
