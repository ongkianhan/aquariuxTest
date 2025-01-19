package com.example.AquariuxTest.Repositories;

import com.example.AquariuxTest.Entity.WalletCurr;
import com.example.AquariuxTest.Entity.WalletCurrPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletCurrencyRepo extends JpaRepository<WalletCurr, WalletCurrPK> {
    WalletCurr findByIdAndTicker(Long id, String ticker);
}
