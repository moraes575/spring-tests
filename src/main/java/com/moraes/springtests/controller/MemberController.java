package com.moraes.springtests.controller;

import com.moraes.springtests.model.Member;
import com.moraes.springtests.service.interfaces.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Member>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Member> save(@Valid @RequestBody Member member) {
        member = service.save(member);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(member.getId()).toUri();
        return ResponseEntity.created(uri).body(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@Valid @RequestBody Member member, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(member, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
