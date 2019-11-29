package com.joseyustiz.earthquakeinfo

import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase
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
    private def earthquake1 = new EarthquakeInfo("Earthquake 1", parse("2019-10-14"), 6.0)
    @Shared
    private def earthquake2 = new EarthquakeInfo("Earthquake 2", parse("2019-10-15"), 7.0)

    def setupSpec() {

        def inMemoryDB = new EarthquakeInfoInMemoryDatabaseAdapter()
        earthquakeInfoService = new GetEarthquakeInfoService(inMemoryDB)
        inMemoryDB.addEarthquakeInfo(earthquake1)
        inMemoryDB.addEarthquakeInfo(earthquake2)
    }

    def "there is no info when there was no earthquake between two dates"() {
        given:
        def startDate = "2019-10-13"
        def endDate = "2019-10-13"

        when:
        def earthquakesInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        earthquakesInfo.isEmpty()
    }

    def "get earthquake info if there was at least one earthquake between two dates"() {
        given:
        def startDate = "2019-10-13"
        def endDate = "2019-10-14"

        when:
        def earthquakesInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        !earthquakesInfo.isEmpty()

    }

    def "get earthquake info from database service between two dates"() {
        given:
        def startDate = "2019-10-13"
        def endDate = "2019-10-15"

        when:
        def earthquakesInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        earthquakesInfo == [earthquake1, earthquake2]

    }

    @Unroll
    def "#howManyEarthquakeInfoThereAre between the magnitudes: '#minMagnitude' and '#maxMagnitude' from database service"() {
        expect:
        earthquakeInfoService.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude) == earthquakesInfo

        where:
        minMagnitude | maxMagnitude | earthquakesInfo
        1.5          | 2.0          | []
        6.5          | 7.0          | [earthquake2]
        6.0          | 7.0          | [earthquake1, earthquake2]

        howManyEarthquakeInfoThereAre = earthquakesInfo.isEmpty() ? "There is no earthquake info " :
                "There is/are info of " + earthquakesInfo.size() + " earthquake(s)"
    }
}
