package jp.expenseapp.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(ExpenseNotFoundException.class)
    public String handleExpenseNotFoundException (HttpServletRequest request,
                                                   ExpenseNotFoundException e,
                                                   Model model) {
        model.addAttribute("notFound", true);
        model.addAttribute("message", e.getMessage());
        return "response";
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalException (HttpServletRequest request,
                                         Exception e,
                                         Model model) {
        model.addAttribute("serverError", true);
        model.addAttribute("message", e.getMessage());
        model.addAttribute("stackTrace", e.getStackTrace());
        return "response";
    }


}
