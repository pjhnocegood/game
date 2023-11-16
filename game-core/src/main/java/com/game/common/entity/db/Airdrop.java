package com.game.common.entity.db;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name = "airdrop")
public class Airdrop extends BaseTimeEntity{

    @Id
    @Column(name = "airdrop_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airdropId;

    @Column(name = "max_count")
    private Long maxCount;

    @Column(name = "count_per_member")
    private Long countPerMember;

    @OneToOne
    @JoinColumn(name = "nft_id")
    private Nft nft;

}

