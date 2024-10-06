package com.example.transactionservice.repository;

import com.example.transactionservice.model.ExpenseCategory;
import com.example.transactionservice.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    @Query("SELECT l FROM Limit l WHERE l.expenseCategory = :expenseCategory AND l.accountId = :accountId ORDER BY l.id DESC LIMIT 1")
    Optional<Limit> findLastLimitByExpenseCategoryAndAccountId(@Param("expenseCategory") ExpenseCategory expenseCategory,
                                                               @Param("accountId") Long accountId);
}
