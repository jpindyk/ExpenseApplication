package jp.expenseapp.service;

import jp.expenseapp.dto.ExpenseDTO;
import jp.expenseapp.dto.ExpenseFilterDTO;
import jp.expenseapp.exception.ExpenseNotFoundException;
import jp.expenseapp.model.Expense;
import jp.expenseapp.repository.ExpenseRepository;
import jp.expenseapp.util.DateTimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ExpenseServiceImplementation implements ExpenseService{

    private ExpenseRepository expenseRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    public ExpenseServiceImplementation(ExpenseRepository expenseRepository, ModelMapper modelMapper, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;

    }
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findByUserId(userService.getLoggedUser().getId())
                .stream()
                .map(e->mapToDTO(e))
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException {
        Expense expense = mapToEntity(expenseDTO);
        expense.setUser(userService.getLoggedUser());
        expenseRepository.save(expense);
        return mapToDTO(expense);
    }

    @Override
    public void deleteExpense(String id) {
        Expense existingExpense = expenseRepository.findByExpenseId(id).orElseThrow(
                () -> new ExpenseNotFoundException("Expense not found for the id: " + id)
        );
        expenseRepository.delete(existingExpense);

    }

    @Override
    public List<ExpenseDTO> getFilteredExpenses(ExpenseFilterDTO expenseFilterDTO) throws ParseException {
        String keyword = expenseFilterDTO.getKeyword();
        String sortBy = expenseFilterDTO.getSortBy();
        String startDateString = expenseFilterDTO.getStartDate();
        String endDateString = expenseFilterDTO.getEndDate();

        Date startDate = !startDateString.isEmpty() ? DateTimeUtil.convertStringToDate(expenseFilterDTO.getStartDate()) :
                new Date(0);
        Date endDate = !endDateString.isEmpty() ? DateTimeUtil.convertStringToDate(expenseFilterDTO.getEndDate()) :
                new Date(System.currentTimeMillis());

        List<Expense> expenses = expenseRepository.findByNameContainingAndDateBetweenAndUserId(
                keyword,
                startDate,
                endDate,
                userService.getLoggedUser().getId());
        List<ExpenseDTO> expenseDTOS = expenses.stream().map(e->mapToDTO(e))
                .collect(Collectors.toList());

        if(sortBy.equals("date")) {
            expenseDTOS.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        } else {
            expenseDTOS.sort((o1, o2) -> o2.getAmount().compareTo(o1.getAmount()));
        }
        return expenseDTOS;
    }

    @Override
    public BigDecimal totalExpenses(List<ExpenseDTO> expenseDTOS) {
        BigDecimal sum = new BigDecimal(0);
        BigDecimal total = expenseDTOS.stream()
                .map(e->e.getAmount().add(sum))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;

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
                () -> new ExpenseNotFoundException("Expense not found for the id: " + id)
        );
        return mapToDTO(existingExpense);
    }
}
