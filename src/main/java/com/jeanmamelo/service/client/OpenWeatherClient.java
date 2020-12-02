package com.jeanmamelo.service.client;

import com.jeanmamelo.configuration.ApplicationConfig;
import com.jeanmamelo.constants.Constants;
import com.jeanmamelo.exception.UnprocessableEntityException;
import com.jeanmamelo.model.dto.OpenWeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenWeatherClient {

    private final ApplicationConfig applicationConfig;
    private final RestTemplateBuilder restTemplateBuilder;

    public OpenWeatherResponse getTemperatureByLocation(String cityName, String lat, String lon) {
        try {
            String url;
            if (!StringUtils.isEmpty(cityName)) {
                log.info("Calling Open Weather API with cityName: {}", cityName);
                url = String.format(applicationConfig.getOpenWeatherEndpointsLocationByCityName(), cityName, applicationConfig.getOpenWeatherCredential());
            } else {
                log.info("Calling Open Weather API with lat: {} lon: {}", lat, lon);
                url = String.format(applicationConfig.getOpenWeatherEndpointsLocationByCoordinates(), lat, lon, applicationConfig.getOpenWeatherCredential());
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

            return restTemplateBuilder.build().exchange(url, HttpMethod.GET,
                    entity, OpenWeatherResponse.class).getBody();
        } catch (Exception e) {
            log.error("Error calling Open Weather API. Details: {} \n Error: {}", e.getMessage(), e);
            throw new UnprocessableEntityException("422.006");
        }
    }
}
