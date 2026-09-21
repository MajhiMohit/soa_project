package com.klu.attendance.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.klu.attendance.dto.MemberResponse;

@FeignClient(name = "member-service")
public interface MemberClient {

    @GetMapping("/members/{id}")
    MemberResponse getMemberById(@PathVariable("id") Long id);
}