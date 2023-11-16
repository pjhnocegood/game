package com.game.common.entity.db;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name = "member")
public class Member extends BaseTimeEntity{

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "email_address", length = 50, unique = true)
    private String emailAddress;

     @Column(name = "member_name", length = 50)
    private String memberName;

    @JsonIgnore
    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @OneToOne(mappedBy = "member")
    private Wallet wallet;

    @OneToMany(mappedBy = "member")
    private List<MemberAuthority> memberAuthorityList;

}

