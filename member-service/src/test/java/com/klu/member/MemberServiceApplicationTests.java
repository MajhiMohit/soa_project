package com.klu.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.klu.member.entity.Member;
import com.klu.member.repository.MemberRepository;
import com.klu.member.service.MemberService;

class MemberServiceApplicationTests {

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberService = new MemberService(memberRepository);
    }

    @Test
    void testGetAllMembers() {

        Member member1 = new Member();
        member1.setId(1L);
        member1.setName("Mohit");
        member1.setEmail("mohit@gmail.com");
        member1.setPhone("9876543210");

        Member member2 = new Member();
        member2.setId(2L);
        member2.setName("Rahul");
        member2.setEmail("rahul@gmail.com");
        member2.setPhone("9876543211");

        when(memberRepository.findAll())
                .thenReturn(Arrays.asList(member1, member2));

        List<Member> result = memberService.getAllMembers();

        assertEquals(2, result.size());
        assertEquals("Mohit", result.get(0).getName());

        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void testGetMemberById() {

        Member member = new Member();
        member.setId(1L);
        member.setName("Mohit");
        member.setEmail("mohit@gmail.com");
        member.setPhone("9876543210");

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        Member result = memberService.getMemberById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mohit", result.getName());

        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateMember() {

        Member member = new Member();
        member.setName("Ravi");
        member.setEmail("ravi@gmail.com");
        member.setPhone("9876543212");

        when(memberRepository.save(member))
                .thenReturn(member);

        Member result = memberService.createMember(member);

        assertNotNull(result);
        assertEquals("Ravi", result.getName());

        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void testUpdateMember() {

        Member existing = new Member();
        existing.setId(1L);
        existing.setName("Old Name");
        existing.setEmail("old@gmail.com");
        existing.setPhone("9876543210");

        Member updated = new Member();
        updated.setName("New Name");
        updated.setEmail("new@gmail.com");
        updated.setPhone("9999999999");

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(memberRepository.save(existing))
                .thenReturn(existing);

        Member result = memberService.updateMember(1L, updated);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("new@gmail.com", result.getEmail());
        assertEquals("9999999999", result.getPhone());

        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).save(existing);
    }

    @Test
    void testDeleteMember() {

        doNothing().when(memberRepository).deleteById(1L);

        memberService.deleteMember(1L);

        verify(memberRepository, times(1)).deleteById(1L);
    }
}