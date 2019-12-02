package com.joseyustiz.earthquakeinfo.adapter.repository

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static java.time.LocalDate.parse

class USGSEarthquakeServiceAdapterSpec extends Specification{
    def "get earthquake info happened between two dates from the USGS Earthquake Service"(){
        given:
        def restTemplate = Mock(RestTemplate)
        restTemplate.getForEntity("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-10-13&endtime=2019-10-13", String.class) >> "{\n" +
                "    \"type\": \"FeatureCollection\",\n" +
                "    \"metadata\": {\n" +
                "        \"generated\": 1575318276000,\n" +
                "        \"url\": \"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-10-13&endtime=2019-10-13\",\n" +
                "        \"title\": \"USGS Earthquakes\",\n" +
                "        \"status\": 200,\n" +
                "        \"api\": \"1.8.1\",\n" +
                "        \"count\": 0\n" +
                "    },\n" +
                "    \"features\": []\n" +
                "}"
        def earthquakeService = new USGSEarthquakeServiceAdapter(restTemplate)

        expect:
        earthquakeService.getInfoBetweenDates(parse(startDate), parse(endDate)) == earthquakesInfo
        where:
        startDate    | endDate      | earthquakesInfo
        "2019-10-13" | "2019-10-13" | []
    }
}
