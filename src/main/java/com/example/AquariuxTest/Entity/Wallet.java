package com.example.AquariuxTest.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="wallet")
public class Wallet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("walletId")
    private Long walletId;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("currencyList")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userWallet")
    private List<WalletCurr> walletCurrList;
}
