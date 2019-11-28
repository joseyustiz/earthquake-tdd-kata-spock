package com.joseyustiz.earthquakettdkataspock

import spock.lang.Specification

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.stream.Collectors

class GetEarthquakeInfoSpec extends Specification {

    def "there is no info when there was no earthquake between two dates"() {
        given:
        GetEarthquakeInfoUseCase earthquakeInfoService = new GetEarthquakeInfoService();
        def startDate = "2019-10-13"
        def endDate = "2019-10-13"

        when:
        def earthquakeInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        earthquakeInfo.isEmpty()
    }

    def "get earthquake info if there was at least one earthquake between two dates"(){
        given:
        GetEarthquakeInfoUseCase earthquakeInfoService = new GetEarthquakeInfoService();
        def startDate = "2019-10-13"
        def endDate = "2019-10-14"

        when:
        def earthquakes = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        !earthquakes.isEmpty()

    }

    interface GetEarthquakeInfoUseCase {
        List<String> getInfoBetweenDates(String startDate, String endDate);
    }

    class GetEarthquakeInfoService implements GetEarthquakeInfoUseCase {
        private Map<LocalDate, List<String>> earthquakes;
        GetEarthquakeInfoService() {
            this.earthquakes = new HashMap();
            earthquakes.put(LocalDate.parse("2019-10-14"),Collections.singletonList("Earthquake 1"));
        }

        List<String> getInfoBetweenDates(String startDate, String endDate) {
            LocalDate sDate= LocalDate.parse(startDate);
            LocalDate eDate= LocalDate.parse(endDate).plusDays(1);

            Set<LocalDate> datesRange = sDate.datesUntil(eDate).collect(Collectors.toSet());
            System.out.println(datesRange);

            List<String> earthquakeInfoInDateRange = new ArrayList();

            for (LocalDate date : datesRange){
                if(earthquakes.containsKey(date))
                earthquakeInfoInDateRange.addAll(earthquakes.get(date));
            }
            return earthquakeInfoInDateRange;
        }
    }
}
