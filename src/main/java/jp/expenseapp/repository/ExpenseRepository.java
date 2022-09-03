package jp.expenseapp.repository;

import jp.expenseapp.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByExpenseId(String id);
    List<Expense> findByNameContainingAndDateBetween (String id, Date startDate, Date endDate);
}
