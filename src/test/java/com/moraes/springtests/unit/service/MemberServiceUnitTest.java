package com.moraes.springtests.unit.service;

import com.moraes.springtests.model.Address;
import com.moraes.springtests.model.Member;
import com.moraes.springtests.repository.MemberRepository;
import com.moraes.springtests.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberServiceUnitTest {

    private static final Member FIRST_MEMBER = new Member(1L, "Name", new Date(), new HashSet<>(), new Address());
    private static final Member SECOND_MEMBER = new Member(2L, "Name", new Date(), new HashSet<>(), new Address());

    @Autowired
    private MemberServiceImpl service;

    @MockBean
    private MemberRepository repository;

    @Test
    void findById() {

        Long id = FIRST_MEMBER.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_MEMBER));

        assertEquals(FIRST_MEMBER, service.findById(id));

    }

    @Test
    void findAll() {

        List<Member> members = new ArrayList<>();
        members.add(FIRST_MEMBER);
        members.add(SECOND_MEMBER);

        when(repository.findAll()).thenReturn(members);

        assertEquals(members, service.findAll());

    }

    @Test
    void save() {

        when(repository.save(FIRST_MEMBER)).thenReturn(FIRST_MEMBER);

        assertEquals(FIRST_MEMBER, service.save(FIRST_MEMBER));

    }

    @Test
    void update() {

        Long id = FIRST_MEMBER.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_MEMBER));
        when(repository.save(FIRST_MEMBER)).thenReturn(FIRST_MEMBER);

        assertEquals(FIRST_MEMBER, service.update(FIRST_MEMBER, id));

    }

    @Test
    void deleteById() {

        Long id = FIRST_MEMBER.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_MEMBER));
        service.deleteById(id);

        then(repository).should().deleteById(id);

    }

}