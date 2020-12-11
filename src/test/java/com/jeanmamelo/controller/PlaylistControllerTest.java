package com.jeanmamelo.controller;

import com.jeanmamelo.exception.UnprocessableEntityException;
import com.jeanmamelo.model.dto.TrackResponse;
import com.jeanmamelo.service.PlaylistService;
import com.jeanmamelo.service.client.RedisClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistControllerTest extends AbstractControllerTest<PlaylistController> {

    private static final String GET_PLAYLISTS = "/playlists";

    @InjectMocks
    private PlaylistController controller;

    @Mock
    private PlaylistService service;

    @Mock
    private RedisClient redis;

    private String cityName;
    private String lat;
    private String lon;

    @Before
    public void startup() {
        super.initializeMvc(controller);
    }

    @Test
    public void shouldReturnATrackResponseWithSuccessWithCityName() throws Exception {
        givenAValidCityName();
        givenPlaylistServiceReturnsATrackResponse();
        whenCallGetPlaylists();
        thenExpectOkStatus();
    }

    @Test
    public void shouldReturnATrackResponseWithSuccessWithLatLon() throws Exception {
        givenAValidLat();
        givenAValidLon();
        givenPlaylistServiceReturnsATrackResponse();
        whenCallGetPlaylists();
        thenExpectOkStatus();
    }

    @Test
    public void shouldReturnATrackResponseWithSuccessFromRedis() throws Exception {
        givenAValidCityName();
        givenPlaylistServiceThrowAnException();
        givenRedisReturnATrackResponse();
        whenCallGetPlaylists();
        thenExpectOkStatus();
    }

    @Test
    public void shouldReturnAnUnprocessableEntityException() throws Exception {
        givenAValidCityName();
        givenPlaylistServiceThrowAnException();
        givenRedisDoesNotReturnATrackResponse();
        whenCallGetPlaylists();
        thenExpectUnprocessableEntityStatus();
    }

    /**
     * Given Methods
     */
    private void givenPlaylistServiceReturnsATrackResponse() {
        doReturn(TrackResponse.builder().build())
                .when(service).getTracks(cityName, lat, lon);
    }

    private void givenAValidCityName() {
        cityName = "Pouso Alegre";
    }

    private void givenPlaylistServiceThrowAnException() {
        doThrow(new RuntimeException()).when(service).getTracks(cityName, lat, lon);
    }

    private void givenAValidLon() {
        lon = "777";
    }

    private void givenAValidLat() {
        lat = "777";
    }

    private void givenRedisReturnATrackResponse() {
        doReturn(Optional.of(TrackResponse.builder().build()))
                .when(redis).get(anyString(), any());
    }

    private void givenRedisDoesNotReturnATrackResponse() {
        doReturn(Optional.empty())
                .when(redis).get(anyString(), any());
    }

    /**
     * When Methods
     */
    private void whenCallGetPlaylists() throws Exception {
        response = mockMvc.perform(get(GET_PLAYLISTS)
                .param("cityName", cityName))
                .andReturn()
                .getResponse();
    }

    /**
     * Then Methods
     */
    private void thenExpectOkStatus() {
        assertEquals(200, response.getStatus());
    }

    private void thenExpectUnprocessableEntityStatus() {
        assertThatThrownBy(this::givenRedisDoesNotReturnATrackResponse).isExactlyInstanceOf(UnprocessableEntityException.class);
    }
}
