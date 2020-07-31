package com.moraes.springtests.service.impl;

import com.moraes.springtests.model.Member;
import com.moraes.springtests.repository.MemberRepository;
import com.moraes.springtests.service.interfaces.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    public MemberServiceImpl(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public Member findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found. ID: " + id));
    }

    @Override
    public List<Member> findAll() {
        return repository.findAll();
    }

    @Override
    public Member save(Member member) {
        return repository.save(member);
    }

    @Override
    public Member update(Member member, Long id) {
        Member oldMember = findById(id);
        if (oldMember.equals(member)) {
            return repository.save(member);
        }
        throw new RuntimeException("Can't update different entities");
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
