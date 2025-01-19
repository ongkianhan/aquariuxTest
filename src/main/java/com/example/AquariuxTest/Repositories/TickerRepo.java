package com.example.AquariuxTest.Repositories;

import com.example.AquariuxTest.Entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TickerRepo extends JpaRepository<Ticker, String> {
    Optional<Ticker> findById(String ticker);
}
