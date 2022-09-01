package jp.expenseapp.service;

import jp.expenseapp.dto.ExpenseDTO;
import jp.expenseapp.model.Expense;
import jp.expenseapp.repository.ExpenseRepository;
import jp.expenseapp.util.DateTimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImplementation implements ExpenseService{

    private ExpenseRepository expenseRepository;
    private ModelMapper modelMapper;

    public ExpenseServiceImplementation(ExpenseRepository expenseRepository, ModelMapper modelMapper) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(e->mapToDTO(e))
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException {
        Expense expense = mapToEntity(expenseDTO);
        expenseRepository.save(expense);
        return mapToDTO(expense);
    }

    @Override
    public void deleteExpense(String id) {
        Expense existingExpense = expenseRepository.findByExpenseId(id).orElseThrow(
                () -> new RuntimeException("Expense not found for the id: " + id)
        );
        expenseRepository.delete(existingExpense);

    }


    private ExpenseDTO mapToDTO(Expense expense) {
        ExpenseDTO expenseDTO = modelMapper.map(expense, ExpenseDTO.class);
        expenseDTO.setDateString(DateTimeUtil.convertDateToString(expenseDTO.getDate()));
        return expenseDTO;
    }



    private Expense mapToEntity(ExpenseDTO expenseDTO) throws ParseException {
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        if (expense.getId() == null) {
            expense.setExpenseId(UUID.randomUUID().toString());
        }
        expense.setDate(DateTimeUtil.convertStringToDate(expenseDTO.getDateString()));
        return  expense;
    }

    public ExpenseDTO getExpenseById (String id) {
        Expense existingExpense = expenseRepository.findByExpenseId(id).orElseThrow(
                () -> new RuntimeException("Expense not found for the id: " + id)
        );
        return mapToDTO(existingExpense);
    }
}
