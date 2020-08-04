package com.moraes.springtests.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moraes.springtests.controller.MemberController;
import com.moraes.springtests.model.Address;
import com.moraes.springtests.model.Member;
import com.moraes.springtests.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerIntegrationTest {

    private static final Member FIRST_MEMBER = new Member(1L, "Name", new Date(), new Address());
    private static final Member SECOND_MEMBER = new Member(2L, "Name", new Date(), new Address());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl service;

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() throws Exception {

        when(service.findById(1L)).thenReturn(FIRST_MEMBER);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/members/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(FIRST_MEMBER.getName())));

    }

    @Test
    void findAll() throws Exception {

        List<Member> members = new ArrayList<>(Arrays.asList(FIRST_MEMBER, SECOND_MEMBER));

        when(service.findAll()).thenReturn(members);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/members")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", greaterThan(1)))
                .andExpect(jsonPath("$.[0].name", is(FIRST_MEMBER.getName())))
                .andExpect(jsonPath("$.[1].name", is(SECOND_MEMBER.getName())));

    }

    @Test
    void save() throws Exception {

        when(service.save(FIRST_MEMBER)).thenReturn(FIRST_MEMBER);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/members")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_MEMBER));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(FIRST_MEMBER.getName())));

    }

    @Test
    void update() throws Exception {

        FIRST_MEMBER.setName("Other member");

        when(service.update(FIRST_MEMBER, 1L)).thenReturn(FIRST_MEMBER);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/members/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_MEMBER));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(FIRST_MEMBER.getName())));

    }

    @Test
    void deleteById() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/members/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_MEMBER));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));

    }

}
