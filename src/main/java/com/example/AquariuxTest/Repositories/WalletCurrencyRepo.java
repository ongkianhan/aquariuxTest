package com.example.AquariuxTest.Repositories;

import com.example.AquariuxTest.Entity.WalletCurr;
import com.example.AquariuxTest.Entity.WalletCurrPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCurrencyRepo extends JpaRepository<WalletCurr, WalletCurrPK> {
    WalletCurr findById(Long id);
}
