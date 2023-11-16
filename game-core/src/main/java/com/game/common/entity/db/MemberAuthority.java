package com.game.common.entity.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Builder
@Entity(name = "member_authority")
public class MemberAuthority extends BaseTimeEntity{

    @Id
    @Column(name = "member_authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberAuthorityId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;



}
