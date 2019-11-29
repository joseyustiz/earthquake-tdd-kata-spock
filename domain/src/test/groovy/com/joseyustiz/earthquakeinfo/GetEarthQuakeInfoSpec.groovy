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
    private def mockedEarthquakeDatabase = Mock(LoadEarthquakeInfoPort)

    def setupSpec() {
        earthquake1 = new EarthquakeInfo("Earthquake 1", parse("2019-10-14"), 6.0)
        earthquake2 = new EarthquakeInfo("Earthquake 2", parse("2019-10-15"), 7.0)

        mockedEarthquakeDatabase.getInfoBetweenDates("2019-10-13", "2019-10-13") >> []
        mockedEarthquakeDatabase.getInfoBetweenDates("2019-10-13", "2019-10-14") >> [earthquake1]
        mockedEarthquakeDatabase.getInfoBetweenDates("2019-10-13", "2019-10-15") >> [earthquake1, earthquake2]

        mockedEarthquakeDatabase.getInfoBetweenMagnitudes(1.5, 2.0) >> []
        mockedEarthquakeDatabase.getInfoBetweenMagnitudes(6.5, 7.0) >> [earthquake2]
        mockedEarthquakeDatabase.getInfoBetweenMagnitudes(6.0, 7.0) >> [earthquake1, earthquake2]

        earthquakeInfoService = new GetEarthquakeInfoService(mockedEarthquakeDatabase)
    }

    @Unroll("#message")
    def "get earthquake info from database service between two dates"() {
        expect:
        earthquakeInfoService.getInfoBetweenDates(startDate, endDate) == earthquakesInfo

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
}
