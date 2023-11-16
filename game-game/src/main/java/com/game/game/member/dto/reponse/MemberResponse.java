package com.game.game.member.dto.reponse;


import com.game.common.entity.db.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
public class MemberResponse {

    private Long memberId;

    private String emailAddress;

    private String memberName;

    private String nickname;

    private boolean activated;


    public static MemberResponse makeMemberResponse(Member member) {
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .emailAddress(member.getEmailAddress())
                .memberName(member.getMemberName())
                .nickname(member.getNickname())
                .activated(member.isActivated())
                .build();
    }
}
