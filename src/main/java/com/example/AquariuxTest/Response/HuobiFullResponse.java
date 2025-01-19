package com.example.AquariuxTest.Response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HuobiFullResponse implements Serializable {
    private String status;
    private BigDecimal ts;
    private List<HuobiResponse> data;

}
