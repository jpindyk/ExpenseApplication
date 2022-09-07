package jp.expenseapp.repository;

import jp.expenseapp.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByExpenseId(String id);
    List<Expense> findByNameContainingAndDateBetweenAndUserId (String id, Date startDate, Date endDate, Long userId);
    List<Expense> findByUserId (Long id);
}
