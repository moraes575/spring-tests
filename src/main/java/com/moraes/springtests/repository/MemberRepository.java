package com.moraes.springtests.repository;

import com.moraes.springtests.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
