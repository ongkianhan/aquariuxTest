package com.example.AquariuxTest.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="walletCurr")
@IdClass(WalletCurrPK.class)
public class WalletCurr implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("walletCurrencyId")
    private Long id;
    @Id
    @JsonProperty("ticker")
    String ticker;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="walletId")
    @JsonProperty("userWallet")
    private Wallet userWallet;

    @JsonProperty("volume")
    private BigDecimal volume; // amount of currency
}
