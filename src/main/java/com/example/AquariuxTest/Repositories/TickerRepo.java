package com.example.AquariuxTest.Repositories;

import com.example.AquariuxTest.Entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepo extends JpaRepository<Ticker, String> {
}
