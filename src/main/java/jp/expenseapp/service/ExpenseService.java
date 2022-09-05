package jp.expenseapp.service;

import jp.expenseapp.dto.ExpenseDTO;
import jp.expenseapp.dto.ExpenseFilterDTO;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface ExpenseService {

    public List<ExpenseDTO> getAllExpenses();
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException;
    public void deleteExpense(String id);
    public ExpenseDTO getExpenseById (String id);

    public List<ExpenseDTO> getFilteredExpenses (ExpenseFilterDTO expenseFilterDTO) throws ParseException;

    public BigDecimal totalExpenses(List<ExpenseDTO> expenseDTOS);
}
