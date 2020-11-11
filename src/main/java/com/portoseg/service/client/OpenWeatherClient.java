package com.portoseg.service.client;

import com.portoseg.configuration.ApplicationConfig;
import com.portoseg.constants.Constants;
import com.portoseg.model.dto.OpenWeatherResponse;
import com.portoseg.model.dto.SpotifyCategoryByIdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public OpenWeatherResponse getTemperatureByCityName(String cityName) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

        String url = String.format(applicationConfig.getOpenWeatherEndpointsLocationByCityName(), cityName, applicationConfig.getOpenWeatherCredential());

        return restTemplateBuilder.build().exchange(url, HttpMethod.GET,
                entity, OpenWeatherResponse.class).getBody();

    }


}
