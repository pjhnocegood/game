package com.game.common.entity.db;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name = "nft_owner")
public class NftOwner extends BaseTimeEntity{

    @Id
    @Column(name = "nft_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(name = "contract_address")
    private String contractAddress;

    @Column(name = "token_id")
    private String tokenId;

    @Column(name = "token_uri")
    private String tokenUri;


}

