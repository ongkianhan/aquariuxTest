package com.example.AquariuxTest.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="walletCurr")
@IdClass(WalletCurrPK.class)
@NoArgsConstructor
@AllArgsConstructor
public class WalletCurr implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @Id
    @JsonProperty("ticker")
    private String ticker;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="walletId")
    @JsonProperty("userWallet")
    private Wallet userWallet;

    @JsonProperty("volume")
    private BigDecimal volume; // amount of currency
}
