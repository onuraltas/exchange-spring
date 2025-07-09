package com.exchange.spring.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exchange")
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeId;

    private Long userId;

    @Column(columnDefinition = "varchar(10)")
    private String sourceCurrencyCode;

    private BigDecimal sourceAmount;

    @Column(columnDefinition = "varchar(10)")
    private String targetCurrencyCode;

    private BigDecimal targetAmount;

}
