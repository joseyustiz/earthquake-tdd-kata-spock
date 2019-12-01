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
    private def service
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

        service = Mock(GetEarthquakeInfoUseCase)
        service.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-13")) >> []
        service.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-14")) >> [earthquake1]
        service.getInfoBetweenDates(parse("2019-10-13"), parse("2019-10-15")) >> [earthquake1, earthquake2]

        controller = new GetEarthquakeInfoController(service)
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
    def "get earthquake info between two magnitudes by colling the Controller"() {
        expect:
        controller.getInfoBetweenMagnitud(minMagnitude, maxMagnitude) == earthquakesInfoWebResponse

        where:
        minMagnitude | maxMagnitude | earthquakesInfoWebResponse
        1.5          | 2.0          | []
        6.5          | 7.0          | ["Earthquake 2"]
        6.0          | 7.0          | ["Earthquake 1", "Earthquake 2"]
        message = "Controller returned " + earthquakesInfoWebResponse.size() + " earthquake(s) info given that happened " + earthquakesInfoWebResponse.size() + " with magnitude between " + minMagnitude + " to " + maxMagnitude
    }

}
