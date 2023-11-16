package com.game.common.entity.db;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name = "airdrop_request_member")
public class AirdropRequestMember extends BaseTimeEntity{

    @Id
    @Column(name = "airdrop_request_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airdropRequestMemberId;

    @Column(name = "airdrop_request_member_key", unique = true)
    private String airdropRequestKey;

    @Column(name = "is_completed")
    @ColumnDefault("false")
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "airdrop_id")
    private Airdrop airdrop;





}

