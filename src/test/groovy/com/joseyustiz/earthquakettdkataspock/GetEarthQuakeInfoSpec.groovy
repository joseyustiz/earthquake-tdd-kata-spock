package com.joseyustiz.earthquakettdkataspock

import spock.lang.Specification

class GetEarthQuakeInfoSpec extends Specification {

    def "there is no earthquake info between two dates"() {
        given:
        LoadEarthQuakeInfoPort earthQuakeInfoReader = new EarthQuakeInfoAdapter();
        def startDate = "2019-10-13"
        def endDate = "2019-10-13"
        when:
        def earthQuakeInfo = earthQuakeInfoReader.getEarthQuakeInfoBetweenDate(startDate, endDate)
        then:
        earthQuakeInfo.isEmpty()
    }

    interface LoadEarthQuakeInfoPort {
        List<String> getEarthQuakeInfoBetweenDate(String startDate, String endDate);
    }

    class EarthQuakeInfoAdapter implements LoadEarthQuakeInfoPort {


        @Override
        List<String> getEarthQuakeInfoBetweenDate(String startDate, String endDate) {
            return new ArrayList();
        }
    }
}
