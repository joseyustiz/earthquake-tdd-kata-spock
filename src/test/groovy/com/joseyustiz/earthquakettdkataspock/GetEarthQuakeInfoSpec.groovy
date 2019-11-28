package com.joseyustiz.earthquakettdkataspock

import spock.lang.Specification

class GetEarthQuakeInfoSpec extends Specification {

    def "there is no earthquake info between two dates"() {
        given:
        GetEarthQuakeInfoUseCase earthQuakeInfoService = new GetEarthQuakeInfoService();
        def startDate = "2019-10-13"
        def endDate = "2019-10-13"

        when:
        def earthQuakeInfo = earthQuakeInfoService.getInfoBetweenDates(startDate, endDate)

        then:
        earthQuakeInfo.isEmpty()
    }

    interface GetEarthQuakeInfoUseCase {
        List<String> getInfoBetweenDates(String startDate, String endDate);
    }

    class GetEarthQuakeInfoService implements GetEarthQuakeInfoUseCase {

        List<String> getInfoBetweenDates(String startDate, String endDate) {
            return new ArrayList();
        }
    }
}
