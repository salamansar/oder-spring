package org.salamansar.oder.module.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.utils.JsonMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Salamansar
 */
@Controller
@RequestMapping("incomes")
@Slf4j
public class IncomeController {
    @Autowired
    private JsonMarshaller jsonMarshaller;
    
    @GetMapping("add")
    public String addIncomeForm() {
        return "addIncome";
    }
    
    @PostMapping("add")
    public String addIncome(@ModelAttribute Income income) {
        String json = jsonMarshaller.toJsonString(income);
        log.info("Income adding received: " + json);
        return "redirect:list";
    }   
    
    @GetMapping("list")
    public String getIncomes(Model model) {
        Income income1 = new Income();
        income1.setAmount(BigDecimal.valueOf(50000));
        income1.setDescription("Some income");
        income1.setDocumentNumber(5);
        income1.setId(1L);
        income1.setIncomeDate(new Date());
        Income income2 = new Income();
        income2.setAmount(BigDecimal.valueOf(150000));
        income2.setDescription("Доход");
        income2.setDocumentNumber(15);
        income2.setId(2L);
        income2.setIncomeDate(new Date());
        model.addAttribute("incomes", Arrays.asList(income1, income2));
        return "listIncomes";
    }
    
}
