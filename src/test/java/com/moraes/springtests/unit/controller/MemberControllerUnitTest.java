package com.moraes.springtests.unit.controller;

import com.moraes.springtests.controller.MemberController;
import com.moraes.springtests.model.Address;
import com.moraes.springtests.model.Member;
import com.moraes.springtests.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberController.class)
public class MemberControllerUnitTest {

    private static final Member FIRST_MEMBER = new Member(1L, "Name", new Date(), new HashSet<>(), new Address());
    private static final Member SECOND_MEMBER = new Member(2L, "Name", new Date(), new HashSet<>(), new Address());

    private MemberController controller;

    @MockBean
    private MemberServiceImpl service;

    @BeforeEach
    void setup() {
        controller = new MemberController(service);
    }

    @Test
    void findById() {

        Long id = FIRST_MEMBER.getId();

        when(service.findById(id)).thenReturn(FIRST_MEMBER);

        assertEquals(HttpStatus.OK, controller.findById(id).getStatusCode());
        assertEquals(FIRST_MEMBER, controller.findById(id).getBody());

    }

    @Test
    void findAll() {

        List<Member> members = new ArrayList<>();
        members.add(FIRST_MEMBER);
        members.add(SECOND_MEMBER);

        when(service.findAll()).thenReturn(members);

        assertEquals(HttpStatus.OK, controller.findAll().getStatusCode());
        assertEquals(members, controller.findAll().getBody());

    }

    @Test
    void save() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(service.save(FIRST_MEMBER)).thenReturn(FIRST_MEMBER);

        assertEquals(HttpStatus.CREATED, controller.save(FIRST_MEMBER).getStatusCode());
        assertEquals(FIRST_MEMBER, controller.save(FIRST_MEMBER).getBody());

    }

    @Test
    void update() {

        Long id = FIRST_MEMBER.getId();

        when(service.update(FIRST_MEMBER, id)).thenReturn(FIRST_MEMBER);

        assertEquals(HttpStatus.OK, controller.update(FIRST_MEMBER, id).getStatusCode());
        assertEquals(FIRST_MEMBER, controller.update(FIRST_MEMBER, id).getBody());

    }

    @Test
    void deleteById() {

        Long id = FIRST_MEMBER.getId();

        assertEquals(HttpStatus.NO_CONTENT, controller.deleteById(id).getStatusCode());

    }

}
