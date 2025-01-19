package com.example.AquariuxTest.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransactionPK implements Serializable {
    private Long userId;
    private Long txnId;
}
