package com.game.game.nft.controller;

import com.game.common.constant.CommonMessageConstant;
import com.game.common.dto.response.ResponseDto;
import com.game.common.util.ResponseUtil;
import com.game.game.nft.dto.AirdropRequest;
import com.game.game.nft.service.AirdropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/nfts")
public class AirdropController {

    private final AirdropService airdropService;

    @PostMapping("airdrop/request")
    public ResponseEntity<ResponseDto> requestAirdrop(@RequestBody AirdropRequest airdropRequest, Principal principal) {
        airdropService.requestAirDrop(airdropRequest,principal);
        return ResponseUtil.SUCCESS_RESPONSE(CommonMessageConstant.SUCCESS, null);
    }

}
