package com.example.AquariuxTest.controller;

import com.example.AquariuxTest.Entity.Transaction;
import com.example.AquariuxTest.Entity.Wallet;
import com.example.AquariuxTest.Repositories.TransactionRepo;
import com.example.AquariuxTest.Repositories.WalletCurrencyRepo;
import com.example.AquariuxTest.Repositories.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @Autowired
    private TransactionRepo txnRepo;

    @Autowired
    private WalletCurrencyRepo walletCurrencyRepo;

    @Autowired
    private WalletRepo walletRepo;

    @GetMapping("/getTxnHistory")
    public ResponseEntity<Object> getTxnHistoryByUserId(@RequestParam Long id) {
        try {
            List<Transaction> txnHist = new ArrayList<>();
            txnHist.addAll(txnRepo.findByUserId(id));
            return ResponseEntity.ok().body(txnHist);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getWallet")
    public ResponseEntity<Object> getWalletByUserId(@RequestParam Long id) {
        try {

            Optional<Wallet> wallet = walletRepo.findById(id);
            return ResponseEntity.ok().body(wallet);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
