package com.game.common.entity.db;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "authority")
public class Authority extends BaseTimeEntity{

    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    @Column(name = "authority_name", length = 50)
    private String authorityName;

    @Column(name = "authority_description", length = 50)
    private String authorityDescription;

    @OneToMany(mappedBy = "authority")
    private List<MemberAuthority> memberAuthority;


}