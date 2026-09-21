package com.klu.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.member.entity.Member;
import com.klu.member.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member member) {
        Member existing = memberRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(member.getName());
            existing.setEmail(member.getEmail());
            existing.setPhone(member.getPhone());
            return memberRepository.save(existing);
        }

        return null;
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}