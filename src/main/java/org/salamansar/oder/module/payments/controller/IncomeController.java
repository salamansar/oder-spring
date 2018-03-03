package org.salamansar.oder.module.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.IncomeService;
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
    @Autowired
    private IncomeService incomeService;
    
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
        User user = new User(); //todo: replace with getting from auth context
        user.setId(1L);
        List<Income> incomes = incomeService.getAllIncomes(user);
        model.addAttribute("incomes", incomes);
        return "listIncomes";
    }
    
}
