package org.salamansar.oder.module.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.utils.JsonMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        log.info("Income adding received: \n" + json);
        return "addIncome";
    }    
}
