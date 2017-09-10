package org.salamansar.oder.module.payments.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Salamansar
 */
@Controller
@RequestMapping("incomes")
public class IncomeController {
    
    @GetMapping("add")
    public String addIncomeForm() {
        return "addIncome";
    }
    
}
