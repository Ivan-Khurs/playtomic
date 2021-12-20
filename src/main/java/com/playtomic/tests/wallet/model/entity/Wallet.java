package com.playtomic.tests.wallet.model.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "wallet",
       indexes = {@Index(name = "idx_uuid", columnList = "uuid"),
       })
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @ColumnDefault("0")
    private BigDecimal balance;

    @CreationTimestamp
    private ZonedDateTime created;

    @UpdateTimestamp
    private ZonedDateTime updated;
    
}
