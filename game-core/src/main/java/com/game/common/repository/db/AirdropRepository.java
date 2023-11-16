package com.game.common.repository.db;

import com.game.common.entity.db.Airdrop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AirdropRepository extends JpaRepository<Airdrop,Long> {

}
