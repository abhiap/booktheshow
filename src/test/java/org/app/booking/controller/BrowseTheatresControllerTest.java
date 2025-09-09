package org.app.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aap.booking.BookTheShowApplication;
import org.aap.booking.controller.BrowseTheatresController;
import org.aap.booking.dto.BrowseTheatresRequest;
import org.aap.booking.service.TheatresService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = BookTheShowApplication.class)
@WebMvcTest(BrowseTheatresController.class)
public class BrowseTheatresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TheatresService theatresService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testBrowseTheatresPost() throws Exception {
        BrowseTheatresRequest request = new BrowseTheatresRequest(1L, 1L, LocalDate.now());
        
        mockMvc.perform(post("/api/v1/browse/theatres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testBrowseTheatresGet() throws Exception {
        mockMvc.perform(get("/api/v1/browse/theatres")
                .param("movieId", "1")
                .param("cityId", "1")
                .param("showDate", LocalDate.now().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testBrowseTheatresNullMovie() throws Exception {
        mockMvc.perform(get("/api/v1/browse/theatres")
                        .param("cityId", "1")
                        .param("showDate", LocalDate.now().toString()))
                .andExpect(status().isBadRequest());
    }
}
