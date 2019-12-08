package com.joseyustiz.earthquakeinfo.adapter.repository


import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Subject

import static java.time.LocalDate.parse

class USGSEarthquakeServiceAdapterSpec extends Specification {
    private static final jsonFeatureEarthquakeInfo1 = "{" + "\"type\":\"Feature\", " + "\"properties\":{" + "\"mag\":0.71999999999999997, " + "\"place\":\"16km NE of Alum Rock, CA\", " + "\"time\":1571010823320, " + "\"updated\":1571781363563, " + "\"tz\":-480, " + "\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/nc73291300\", " + "\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nc73291300&format=geojson\", " + "\"felt\":null, " + "\"cdi\":null, " + "\"mmi\":null, " + "\"alert\":null, " + "\"status\":\"reviewed\", " + "\"tsunami\":0, " + "\"sig\":8, " + "\"net\":\"nc\", " + "\"code\":\"73291300\", " + "\"ids\":\",nc73291300,\", " + "\"sources\":\",nc,\", " + "\"types\":\",geoserve,nearby-cities,origin,phase-data,scitech-link,\", " + "\"nst\":14, " + "\"dmin\":0.037810000000000003, " + "\"rms\":0.059999999999999998, " + "\"gap\":82, " + "\"magType\":\"md\", " + "\"type\":\"earthquake\", " + "\"title\":\"M 0.7 - 16km NE of Alum Rock, CA\"" + "}, " + "\"geometry\":{" + "\"type\":\"Point\", " + "\"coordinates\":[" + "-121.68766669999999," + "37.451666699999997," + "8.8300000000000001" + "]" + "}, " + "\"id\":\"nc73291300\"" + "}"
    private static final jsonFeatureEarthquakeInfo2 = "{" + "\"type\":\"Feature\"," + "\"properties\":{" + " \"mag\":0.90000000000000002," + " \"place\":\"8km ESE of Ridgecrest, CA\"," + " \"time\":1571010727420," + " \"updated\":1571062807070," + " \"tz\":-480," + " \"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ci39130488\"," + " \"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ci39130488&format=geojson\"," + " \"felt\":null," + " \"cdi\":null," + " \"mmi\":null," + " \"alert\":null," + " \"status\":\"reviewed\"," + " \"tsunami\":0," + " \"sig\":12," + " \"net\":\"ci\"," + " \"code\":\"39130488\"," + " \"ids\":\",ci39130488,\"," + " \"sources\":\",ci,\"," + " \"types\":\",geoserve,nearby-cities,origin,phase-data,scitech-link,\"," + " \"nst\":13," + " \"dmin\":0.10349999999999999," + " \"rms\":0.17000000000000001," + " \"gap\":102," + " \"magType\":\"ml\"," + " \"type\":\"earthquake\"," + " \"title\":\"M 0.9 - 8km ESE of Ridgecrest, CA\"" + "}," + "\"geometry\":{" + " \"type\":\"Point\"," + " \"coordinates\":[" + "-117.587," + "35.6056667," + "7.79" + "]" + "}," + "\"id\":\"ci39130488\"" + "}"

    private static final EarthquakeInfo earthquakeInfo1 = EarthquakeInfo.builder().info(jsonFeatureEarthquakeInfo1.replace(" \"", "\"")).date(parse("2019-10-13")).magnitude(0.71999999999999997).country("CA").build()
    private static final EarthquakeInfo earthquakeInfo2 = EarthquakeInfo.builder().info(jsonFeatureEarthquakeInfo2.replace(" \"", "\"")).date(parse("2019-10-13")).magnitude(0.90000000000000002).country("CA").build()
    private static final String USGSE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson"

    private def featureEarthquakeInfo1 = Feature.builder().id("nc73291300").type("Feature").geometry(Geometry.builder().type("Point").coordinates([-121.68766669999999, 37.451666699999997, 8.8300000000000001]).build()).properties(Property.builder().mag(0.71999999999999997).place("16km NE of Alum Rock, CA").time(1571010823320).updated(1571781363563).tz(-480).url("https://earthquake.usgs.gov/earthquakes/eventpage/nc73291300").detail("https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=nc73291300&format=geojson").felt(null).cdi(null).mmi(null).alert(null).status("reviewed").tsunami(0).sig(8).net("nc").code("73291300").ids(",nc73291300,").sources(",nc,").types(",geoserve,nearby-cities,origin,phase-data,scitech-link,").nst(14).dmin(0.037810000000000003).rms(0.059999999999999998).gap(82).magType("md").type("earthquake").title("M 0.7 - 16km NE of Alum Rock, CA").build()).build()
    private def featureEarthquakeInfo2 = Feature.builder().id("ci39130488").type("Feature").geometry(Geometry.builder().type("Point").coordinates([-117.587, 35.6056667, 7.79]).build()).properties(Property.builder().mag(0.90000000000000002).place("8km ESE of Ridgecrest, CA").time(1571010727420).updated(1571062807070).tz(-480).url("https://earthquake.usgs.gov/earthquakes/eventpage/ci39130488").detail("https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ci39130488&format=geojson").felt(null).cdi(null).mmi(null).alert(null).status("reviewed").tsunami(0).sig(12).net("ci").code("39130488").ids(",ci39130488,").sources(",ci,").types(",geoserve,nearby-cities,origin,phase-data,scitech-link,").nst(13).dmin(0.10349999999999999).rms(0.17000000000000001).gap(102).magType("ml").type("earthquake").title("M 0.9 - 8km ESE of Ridgecrest, CA").build()).build()

    private RestTemplate mockedRestTemplate = Mock(RestTemplate)

    @Subject
    private def earthquakeService = new USGSEarthquakeServiceAdapter(mockedRestTemplate, new URL(USGSE_URL))

    def "get earthquake info happened between two dates from the USGS Earthquake Service"() {
        given:
        mockedRestTemplate.getForObject(USGSE_URL + "&starttime=2019-10-13&endtime=2019-10-13", UUGSEarthquakeInfoQueryResult.class) >> new UUGSEarthquakeInfoQueryResult(Collections.emptyList())
        mockedRestTemplate.getForObject(USGSE_URL + "&starttime=2019-10-13&endtime=2019-10-14", UUGSEarthquakeInfoQueryResult.class) >> new UUGSEarthquakeInfoQueryResult([featureEarthquakeInfo1, featureEarthquakeInfo2])

        expect:
        earthquakeService.getInfoBetweenDates(parse(startDate), parse(endDate)).toString() == earthquakesInfo.toString()
        where:
        startDate    | endDate      | earthquakesInfo
        "2019-10-13" | "2019-10-13" | []
        "2019-10-13" | "2019-10-14" | [earthquakeInfo1, earthquakeInfo2]
    }

    def "get earthquake info between two magnitudes from the USGS Earthquake Service"() {
        given:
        mockedRestTemplate.getForObject(USGSE_URL + "&minmagnitude=1.95&maxmagnitude=2.0", UUGSEarthquakeInfoQueryResult.class) >> new UUGSEarthquakeInfoQueryResult(Collections.emptyList())
        mockedRestTemplate.getForObject(USGSE_URL + "&minmagnitude=0.7&maxmagnitude=1.0", UUGSEarthquakeInfoQueryResult.class) >> new UUGSEarthquakeInfoQueryResult([featureEarthquakeInfo1, featureEarthquakeInfo2])

        expect:
        earthquakeService.getInfoBetweenMagnitudes(minMagnitude, maxMagnitude).toString() == earthquakesInfo.toString()
        where:
        minMagnitude | maxMagnitude | earthquakesInfo
        1.95         | 2.0          | []
        0.7          | 1.0          | [earthquakeInfo1, earthquakeInfo2]
    }

    def "get all earthquake info from the USGS Earthquake Service"() {
        given:
        mockedRestTemplate.getForObject(USGSE_URL, UUGSEarthquakeInfoQueryResult.class) >> new UUGSEarthquakeInfoQueryResult([featureEarthquakeInfo1, featureEarthquakeInfo2])

        expect:
        earthquakeService.getAllEarthquakesInfo().toString() == earthquakesInfo.toString()

        where:
        earthquakesInfo << [earthquakeInfo1, earthquakeInfo2]
    }
}
