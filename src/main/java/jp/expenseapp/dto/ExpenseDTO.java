package jp.expenseapp.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

    private Long id;
    private String expenseId;
    private String name;
    private String description;
    private BigDecimal amount;
    private Date date;
    private String dateString;

}
