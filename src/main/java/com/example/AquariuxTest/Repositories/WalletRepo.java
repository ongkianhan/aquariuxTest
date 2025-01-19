package com.example.AquariuxTest.Repositories;

import com.example.AquariuxTest.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet, Long> {

}
