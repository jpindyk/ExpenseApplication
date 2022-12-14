package jp.expenseapp.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

    private Long id;
    private String expenseId;
    @NotBlank(message = "Expanse name should not be empty")
    @Size(min=3, message = "Expanse name should have at least {min} characters")
    private String name;

    private String description;
    @NotNull(message = "Expense amount should not be empty")
    @Min(value = 1, message = "Expense amount should not be less than 1")
    private BigDecimal amount;
    private Date date;
    private String dateString;

}
