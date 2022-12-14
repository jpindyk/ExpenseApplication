package jp.expenseapp.controller;

import jp.expenseapp.dto.ExpenseDTO;
import jp.expenseapp.dto.ExpenseFilterDTO;
import jp.expenseapp.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.ParseException;
import java.util.List;

@Controller
public class ExpenseFilterController {

    private ExpenseService expenseService;

    public ExpenseFilterController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/filterExpenses")
    public String filterExpenses(@ModelAttribute("filter")ExpenseFilterDTO expenseFilterDTO,
                                 Model model) throws ParseException {
        System.out.println("Printing the filter dto: " + expenseFilterDTO);
        List<ExpenseDTO> list = expenseService.getFilteredExpenses(expenseFilterDTO);
        model.addAttribute("expenses", list);
        model.addAttribute("sum", expenseService.totalExpenses(list));
        return "expense-list";
    }

}
