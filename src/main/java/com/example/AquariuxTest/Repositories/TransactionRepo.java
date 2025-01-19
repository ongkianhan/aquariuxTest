package com.example.AquariuxTest.Repositories;

import com.example.AquariuxTest.Entity.Transaction;
import com.example.AquariuxTest.Entity.TransactionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, TransactionPK> {
    public List<Transaction> findByUserId(Long id);
}
