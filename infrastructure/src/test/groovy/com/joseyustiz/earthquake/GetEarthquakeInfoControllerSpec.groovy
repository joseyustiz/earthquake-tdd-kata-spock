package com.joseyustiz.earthquake

import com.joseyustiz.earthquakeinfo.adapter.web.GetEarthquakeInfoController
import com.joseyustiz.earthquakeinfo.application.port.in.GetEarthquakeInfoUseCase
import com.joseyustiz.earthquakeinfo.model.EarthquakeInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification

import static java.time.LocalDate.parse
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = Config.class)
@WebMvcTest(controllers = GetEarthquakeInfoController.class)
class GetEarthquakeInfoControllerSpec extends Specification {
    @Autowired
    private MockMvc mockMvc
    @MockBean
    private GetEarthquakeInfoUseCase service

    @Shared
    private def earthquake1
    @Shared
    private def earthquake2
    @Shared
    private def earthquake3
    @Shared
    private def earthquake4


    void setup() {
        earthquake1 = new EarthquakeInfo("Earthquake 1", parse("2019-10-14"), 6.0, "Mexico")
        earthquake2 = new EarthquakeInfo("Earthquake 2", parse("2019-10-15"), 7.0, "Chile")

        earthquake3 = new EarthquakeInfo("Earthquake 3", parse("2019-10-16"), 8.0, "Japan")
        earthquake4 = new EarthquakeInfo("Earthquake 4", parse("2019-10-17"), 9.0, "Japan")

    }

    def "get earthquake info from database service between two dates by calling REST API"() {
        given:
        service.getInfoBetweenDates(parse(startDate),parse(endDate))
        expect: "Status is 200 and the response"
        mockMvc.perform(get("/earthquake/searchByDate"))
                .andExpect(status().isOk())
                .andReturn().response == earthquakesInfo
        where:
        startDate    | endDate      | earthquakesInfo
        "2019-10-13" | "2019-10-13" | []
        "2019-10-13" | "2019-10-14" | [earthquake1]
        "2019-10-13" | "2019-10-15" | [earthquake1, earthquake2]
    }
}
