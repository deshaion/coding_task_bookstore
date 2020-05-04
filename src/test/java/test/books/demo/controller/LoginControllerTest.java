package test.books.demo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import test.books.demo.controller.request.Credentials;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void success() throws Exception {
        assertRequest(status().isOk(), new Credentials("test@gmail.com", "password"), "{\"status\": \"success\"}");
    }

    @Test
    void emptyEmail() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("", "password"), "{\"status\": \"error\", \"message\": \"Email is empty.\"}");
    }

    @Test
    void emptyPassword() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("test@gmail.com", ""), "{\"status\": \"error\", \"message\": \"Password is empty.\"}");
    }

    @Test
    void emptyEmailAndPassword() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("", ""), "{\"status\": \"error\", \"message\": \"Email is empty. Password is empty.\"}");
    }

    @Test
    void testEmail() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("test@test.com", "password"), "{\"status\": \"error\", \"message\": \"E-Mails from test.com are invalid.\"}");
    }

    @Test
    void notValidEmail1() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("@test.com", "password"),
                "{\"status\": \"error\", \"message\": \"E-Mail is not valid. Check the format email@mail.com\"}");
    }

    @Test
    void notValidEmail2() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("abc@test", "password"),
                "{\"status\": \"error\", \"message\": \"E-Mail is not valid. Check the format email@mail.com\"}");
    }

    @Test
    void notValidEmail3() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("abc@test.", "password"),
                "{\"status\": \"error\", \"message\": \"E-Mail is not valid. Check the format email@mail.com\"}");
    }

    @Test
    void notValidEmail4() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("abc.test@com", "password"),
                "{\"status\": \"error\", \"message\": \"E-Mail is not valid. Check the format email@mail.com\"}");
    }

    @Test
    void notValidEmail5() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("abc", "password"),
                "{\"status\": \"error\", \"message\": \"E-Mail is not valid. Check the format email@mail.com\"}");
    }

    @Test
    void notValidEmail6() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("abc@mail..com", "password"),
                "{\"status\": \"error\", \"message\": \"E-Mail is not valid. Check the format email@mail.com\"}");
    }

    @Test
    void notValidEmail7() throws Exception {
        assertRequest(status().isBadRequest(), new Credentials("abc@mail.a", "password"),
                "{\"status\": \"error\", \"message\": \"E-Mail is not valid. Check the format email@mail.com\"}");
    }

    private void assertRequest(ResultMatcher status, Credentials credentials, String expectedJson) throws Exception {
        assertRequest(status, objectMapper.writeValueAsString(credentials), expectedJson);
    }

    private void assertRequest(ResultMatcher status, String requestJson, String expectedJson) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<>() {};
        Map<String, Object> actual = objectMapper.readValue(contentAsString, typeRef);

        Map<String, Object> expected = objectMapper.readValue(expectedJson, typeRef);

        assertEquals(expected, actual);
    }
}