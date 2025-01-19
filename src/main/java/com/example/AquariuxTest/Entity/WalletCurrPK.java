package com.example.AquariuxTest.Entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class WalletCurrPK implements Serializable {
    private Long id;
    private String ticker;
}
