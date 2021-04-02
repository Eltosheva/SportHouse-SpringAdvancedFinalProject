package com.eltosheva.sporthouse.bindingModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class SubscriptionBindingModel {
    @NotBlank(message = "Enter training plan name")
    private String name;
    private BigDecimal price;
    private Integer trainingCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;
    private Boolean isActive;
}
