package jp.expenseapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseFilterDTO {

    public ExpenseFilterDTO(String startDate, String endDate){
        this.startDate=startDate;
        this.endDate=endDate;
    }
    private String keyword;
    private String sortBy;
    private String startDate;
    private String endDate;

}
