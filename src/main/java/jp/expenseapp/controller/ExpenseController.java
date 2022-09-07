package jp.expenseapp.controller;

import jp.expenseapp.dto.ExpenseDTO;
import jp.expenseapp.dto.ExpenseFilterDTO;
import jp.expenseapp.model.Expense;
import jp.expenseapp.service.ExpenseService;
import jp.expenseapp.util.DateTimeUtil;
import jp.expenseapp.validator.ExpenseValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@Controller
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public String showExpenseList(Model model){
        List<ExpenseDTO> expenseDTOS = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenseDTOS);
        model.addAttribute("filter", new ExpenseFilterDTO(DateTimeUtil.getCurrentMonthStartDate(), DateTimeUtil.getCurrentMonthDate()));
        model.addAttribute("sum", expenseService.totalExpenses(expenseDTOS));
        return "expense-list";
    }

    @GetMapping("/createExpense")
    public String showExpenseForm(Model model) {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setDateString(DateTimeUtil.getCurrentMonthDate());
        model.addAttribute("expense", expenseDTO);
        return "expense-form";
    }
    @PostMapping("/saveOrUpdateExpense")
    public String saveOrUpdateExpenseDetails(@Valid @ModelAttribute("expense") ExpenseDTO expenseDTO,
                                             BindingResult result) throws ParseException {
        System.out.println("Printing the Expense DTO: " + expenseDTO);

        new ExpenseValidator().validate(expenseDTO, result);

        if (result.hasErrors()){
            return "expense-form";
        }
        expenseService.saveExpenseDetails(expenseDTO);
        return "redirect:/expenses";
    }

    @GetMapping("/deleteExpense")
    public String deleteExpense(@RequestParam String id) {
        System.out.println("Printing the expense Id: " + id);
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }

    @GetMapping("/updateExpense")
    public String updateExpense(@RequestParam String id,
                                Model model) {
        System.out.println("Printing the expense Id: " + id);
        ExpenseDTO expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "expense-form";
    }




}
