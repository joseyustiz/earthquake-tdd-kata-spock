package com.joseyustiz.earthquake

import com.joseyustiz.earthquake.application.service.GetEarthquakeInfoService
import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase
import spock.lang.Specification

class GetEarthquakeInfoSpec extends Specification {
    GetEarthquakeInfoUseCase earthquakeInfoService;

    def setup() {
        earthquakeInfoService = new GetEarthquakeInfoService(new EarthquakeInfoInMemoryDatabaseAdapter());
    }

    def "there is no info when there was no earthquake between two dates"() {
        given:
//        GetEarthquakeInfoUseCase earthquakeInfoService = new GetEarthquakeInfoService(new EarthquakeInfoInMemoryDatabaseAdapter());
        def startDate = "2019-10-13"
        def endDate = "2019-10-13"

        when:
        def earthquakeInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        earthquakeInfo.isEmpty()
    }

    def "get earthquake info if there was at least one earthquake between two dates"(){
        given:
//        GetEarthquakeInfoUseCase earthquakeInfoService = new GetEarthquakeInfoService(new EarthquakeInfoInMemoryDatabaseAdapter());
        def startDate = "2019-10-13"
        def endDate = "2019-10-14"

        when:
        def earthquakes = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        !earthquakes.isEmpty()

    }

    def "get earthquake info from database service between two dates"(){
        given:
        def startDate = "2019-10-13"
        def endDate = "2019-10-15"
//        GetEarthquakeInfoUseCase earthquakeInfoService = new GetEarthquakeInfoService(new EarthquakeInfoInMemoryDatabaseAdapter());

        when:
        def earthquakesInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        earthquakesInfo == ["Earthquake 1", "Earthquake 2"]

    }


}
