package com.example.AquariuxTest.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Ticker {
    @Id
    String symbol;
    double open;
    double high;
    double low;
    double close;

}
