package com.joseyustiz.earthquakeinfo

import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase
import com.joseyustiz.earthquakeinfo.application.service.GetEarthquakeInfoService
import spock.lang.Shared
import spock.lang.Specification

class GetEarthquakeInfoSpec extends Specification {
    @Shared private GetEarthquakeInfoUseCase earthquakeInfoService;

    def setupSpec() {
        def inMemoryDB = new EarthquakeInfoInMemoryDatabaseAdapter()
        earthquakeInfoService = new GetEarthquakeInfoService(inMemoryDB)
        inMemoryDB.addEarthquakeInfo("2019-10-14", Collections.singletonList("Earthquake 1"))
        inMemoryDB.addEarthquakeInfo("2019-10-15", Collections.singletonList("Earthquake 2"))
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

    def "get earthquake info if there was at least one earthquake between two dates"(){
        given:
        def startDate = "2019-10-13"
        def endDate = "2019-10-14"

        when:
        def earthquakesInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        !earthquakesInfo.isEmpty()

    }

    def "get earthquake info from database service between two dates"(){
        given:
        def startDate = "2019-10-13"
        def endDate = "2019-10-15"

        when:
        def earthquakesInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        earthquakesInfo == ["Earthquake 1", "Earthquake 2"]

    }


}
