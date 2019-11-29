package com.joseyustiz.earthquake

import com.joseyustiz.earthquake.application.service.GetEarthquakeInfoService
import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase
import com.joseyustiz.earthquakeinfo.application.port.out.LoadEarthquakeInfoPort
import spock.lang.Specification

import java.time.LocalDate
import java.util.stream.Collectors

class GetEarthquakeInfoSpec extends Specification {

    def "there is no info when there was no earthquake between two dates"() {
        given:
        GetEarthquakeInfoUseCase earthquakeInfoService = new GetEarthquakeInfoService(new EarthquakeInfoInMemoryDatabaseAdapter());
        def startDate = "2019-10-13"
        def endDate = "2019-10-13"

        when:
        def earthquakeInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        earthquakeInfo.isEmpty()
    }

    def "get earthquake info if there was at least one earthquake between two dates"(){
        given:
        GetEarthquakeInfoUseCase earthquakeInfoService = new GetEarthquakeInfoService(new EarthquakeInfoInMemoryDatabaseAdapter());
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
        GetEarthquakeInfoUseCase earthquakeInfoService = new GetEarthquakeInfoService(new EarthquakeInfoInMemoryDatabaseAdapter());

        when:
        def earthquakesInfo = earthquakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        earthquakesInfo == ["Earthquake 1", "Earthquake 2"]

    }
    class EarthquakeInfoInMemoryDatabaseAdapter implements LoadEarthquakeInfoPort{
        private Map<LocalDate, List<String>> earthquakes;

        EarthquakeInfoInMemoryDatabaseAdapter() {
            this.earthquakes = new HashMap();
            earthquakes.put(LocalDate.parse("2019-10-14"),Collections.singletonList("Earthquake 1"));
            earthquakes.put(LocalDate.parse("2019-10-15"),Collections.singletonList("Earthquake 2"));
        }

        @Override
        List<String> getInfoBetweenDates(String startDate, String endDate) {
            LocalDate sDate= LocalDate.parse(startDate);
            LocalDate eDate= LocalDate.parse(endDate).plusDays(1);

            List<String> earthquakeInfoInDateRange = new ArrayList();

            for (LocalDate date : getDateRange(sDate, eDate)){
                if(earthquakes.containsKey(date))
                    earthquakeInfoInDateRange.addAll(earthquakes.get(date));
            }
            Collections.sort(earthquakeInfoInDateRange);
            return earthquakeInfoInDateRange;
        }

        private Set<LocalDate> getDateRange(LocalDate sDate, LocalDate eDate) {
            sDate.datesUntil(eDate).collect(Collectors.toSet())
        }
    }


}
