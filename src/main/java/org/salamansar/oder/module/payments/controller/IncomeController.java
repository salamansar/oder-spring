package org.salamansar.oder.module.payments.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.payments.adapter.IncomeAdapter;
import org.salamansar.oder.module.payments.dto.IncomeDto;
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
public class IncomeController {//todo: unit test
    @Autowired
    private JsonMarshaller jsonMarshaller;
	@Autowired
	private IncomeAdapter incomeAdapter;
    
    @GetMapping("add")
    public String addIncomeForm() {
        return "addIncome";
    }
    
    @PostMapping("add")
    public String addIncome(@ModelAttribute IncomeDto income) {
        String json = jsonMarshaller.toJsonString(income);
        log.info("Income adding received: " + json);
        User user = new User(); //todo: receive user from auth context
        user.setId(1L);
        //todo: check data before saving
        incomeAdapter.addIncome(user, income);
        return "redirect:list";
    }   
    
    @GetMapping("list")
    public String getIncomes(Model model) {
        User user = new User(); //todo: replace with getting from auth context
        user.setId(1L);
		List<IncomeDto> incomes = incomeAdapter.getAllIncomes(user);
        model.addAttribute("incomes", incomes);
        return "listIncomes";
    }
    
}
