package jp.expenseapp.service;

import jp.expenseapp.dto.ExpenseDTO;

import java.text.ParseException;
import java.util.List;

public interface ExpenseService {

    public List<ExpenseDTO> getAllExpenses();
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException;
    void deleteExpense(String id);
}
