package com.example.AquariuxTest.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transactions")
@ToString
@IdClass(TransactionPK.class)
@Slf4j
public class Transaction implements Serializable {
    @Id
    @JsonProperty("userId")
    private Long userId;
    @Id
    @JsonProperty("txnId")
    private Long txnId;
    @JsonProperty("ticker")
    private String ticker;
    @JsonProperty("txnTime")
    private LocalDateTime txnTime;
    @JsonProperty("txnVolume")
    private BigDecimal txnVolume;
    @JsonProperty("txnPrice")
    private BigDecimal txnPrice;
    @JsonProperty("totalAmtTxn")
    private BigDecimal totalAmtTxn; //total amount transacted
    @JsonProperty("txnType")
    private String txnType; // either buy(B) or sell(S)


}
