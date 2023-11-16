package com.game.common.entity.db;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name = "wallet")
public class Wallet extends BaseTimeEntity{

    @Id
    @Column(name = "wallet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(name = "wallet_address",unique = true)
    private String walletAddress;

    @Column(name = "wallet_owner_type")
    private String walletOwnerType;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

}

