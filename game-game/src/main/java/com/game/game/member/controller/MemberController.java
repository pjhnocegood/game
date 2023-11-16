package com.game.game.member.controller;


import com.game.common.constant.CommonMessageConstant;
import com.game.common.dto.response.ResponseDto;
import com.game.common.util.ResponseUtil;
import com.game.game.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;



    @GetMapping("profile")
    public ResponseEntity<ResponseDto> getMemberProfile(Principal principal) {
        return ResponseUtil.SUCCESS_RESPONSE(CommonMessageConstant.SUCCESS, memberService.getMemberProfile(principal));
    }


}
