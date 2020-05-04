package test.books.demo.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import test.books.demo.settings.Settings;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Settings settings;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is("b1")))
                .andExpect(jsonPath("$[0].name", is("Textbook A:  Unit testing basic principles")))
                .andExpect(jsonPath("$[0].price", comparesEqualTo(75.234)))
                .andExpect(jsonPath("$[0].image", is("https://bilder.buecher.de/produkte/42/42530/42530510z.jpg")))
                .andExpect(jsonPath("$[1].id", is("b2")))
                .andExpect(jsonPath("$[1].name", is("Textbook B:  Machine learning fundamentals")))
                .andExpect(jsonPath("$[1].price", comparesEqualTo(23.34)))
                .andExpect(jsonPath("$[1].image", is("https://bilder.buecher.de/produkte/48/48193/48193654z.jpg")))
                .andExpect(jsonPath("$[2].id", is("b3")))
                .andExpect(jsonPath("$[2].name", is("Textbook C:  Domain driven design")))
                .andExpect(jsonPath("$[2].price", comparesEqualTo(120.)))
                .andExpect(jsonPath("$[2].image", is("https://bilder.buecher.de/produkte/49/49065/49065197z.jpg")));
    }

    @Test
    void getB1() throws Exception {
        Mockito.when(settings.getSimLevel()).thenReturn(0.5);

        mockMvc.perform(get("/api/books/b1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("b1")))
                .andExpect(jsonPath("$.name", is("Textbook A:  Unit testing basic principles")))
                .andExpect(jsonPath("$.price", comparesEqualTo(75.234)))
                .andExpect(jsonPath("$.image", is("https://bilder.buecher.de/produkte/42/42530/42530510z.jpg")))
                .andExpect(jsonPath("$.details", is("lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum")))
                .andExpect(jsonPath("$.similarBooks[0].id", is("b2")))
                .andExpect(jsonPath("$.similarBooks[0].name", is("Textbook B:  Machine learning fundamentals")))
                .andExpect(jsonPath("$.similarBooks[0].price", comparesEqualTo(23.34)))
                .andExpect(jsonPath("$.similarBooks[0].image", is("https://bilder.buecher.de/produkte/48/48193/48193654z.jpg")));
    }

    @Test
    void getB1WithLessSimilarity() throws Exception {
        Mockito.when(settings.getSimLevel()).thenReturn(0.1);

        mockMvc.perform(get("/api/books/b1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("b1")))
                .andExpect(jsonPath("$.name", is("Textbook A:  Unit testing basic principles")))
                .andExpect(jsonPath("$.price", comparesEqualTo(75.234)))
                .andExpect(jsonPath("$.image", is("https://bilder.buecher.de/produkte/42/42530/42530510z.jpg")))
                .andExpect(jsonPath("$.details", is("lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum, lorem ipsum")))
                .andExpect(jsonPath("$.similarBooks[0].id", is("b2")))
                .andExpect(jsonPath("$.similarBooks[0].name", is("Textbook B:  Machine learning fundamentals")))
                .andExpect(jsonPath("$.similarBooks[0].price", comparesEqualTo(23.34)))
                .andExpect(jsonPath("$.similarBooks[0].image", is("https://bilder.buecher.de/produkte/48/48193/48193654z.jpg")))
                .andExpect(jsonPath("$.similarBooks[1].id", is("b3")))
                .andExpect(jsonPath("$.similarBooks[1].name", is("Textbook C:  Domain driven design")))
                .andExpect(jsonPath("$.similarBooks[1].price", comparesEqualTo(120.)))
                .andExpect(jsonPath("$.similarBooks[1].image", is("https://bilder.buecher.de/produkte/49/49065/49065197z.jpg")));
    }
}