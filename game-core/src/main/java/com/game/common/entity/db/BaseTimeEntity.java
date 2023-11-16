package com.game.common.entity.db;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.ZonedDateTime;



@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {
    @Column(name = "first_create_datetime")
    private ZonedDateTime firstCreateDatetime;

    @Column(name = "last_update_datetime")
    private ZonedDateTime lastUpdateDatetime;


    @PrePersist
    public void prePersist() {
        this.firstCreateDatetime = ZonedDateTime.now();
        this.lastUpdateDatetime = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdateDatetime = ZonedDateTime.now();
    }
}
