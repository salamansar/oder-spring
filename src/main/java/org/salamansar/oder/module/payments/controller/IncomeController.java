package org.salamansar.oder.module.payments.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.common.FillUserInfo;
import org.salamansar.oder.security.CurrentUser;
import org.salamansar.oder.module.payments.adapter.IncomeAdapter;
import org.salamansar.oder.module.payments.dto.IncomeDto;
import org.salamansar.oder.utils.JsonMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Salamansar
 */
@Controller
@RequestMapping("payments/incomes")
@Slf4j
public class IncomeController {//todo: unit test
    @Autowired
    private JsonMarshaller jsonMarshaller;
	@Autowired
	private IncomeAdapter incomeAdapter;
    
    @GetMapping("add")
	@FillUserInfo
    public String addIncomeForm(@CurrentUser User user, Model model) {
        return "addIncome";
    }
    
    @PostMapping("add")
    public String addIncome(@ModelAttribute IncomeDto income, @CurrentUser User user) {
        String json = jsonMarshaller.toJsonString(income);
        log.info("Income adding received: " + json);
        //todo: check data before saving
        incomeAdapter.addIncome(user, income);
        return "redirect:list";
    }   
    
    @GetMapping("list")
	@FillUserInfo
    public String getIncomes(Model model, @CurrentUser User user) {
		List<IncomeDto> incomes = incomeAdapter.getAllIncomes(user);
        model.addAttribute("incomes", incomes);
        return "listIncomes";
    }
    
	@GetMapping("edit/{id}")
	@FillUserInfo
	public String editIncomeForm(@PathVariable("id") Long id, @CurrentUser User user, Model model) {
		IncomeDto income = incomeAdapter.getIncome(user, id);
		model.addAttribute("income", income);
		model.addAttribute("mode", "edit");
		return "addIncome";
	}
	
	@PostMapping("edit/{id}")
	public String editIncome(@ModelAttribute IncomeDto income, @PathVariable("id") Long id, @CurrentUser User user) {
		String json = jsonMarshaller.toJsonString(income);
		income.setId(id);
		log.info("Income editing received: " + json);
		//todo: check data before saving
		incomeAdapter.editIncome(user, income);
		return "redirect:../list";
	}
	
	@GetMapping("delete/{id}")
	public String deleteIncome(@PathVariable("id") Long id, @CurrentUser User user) {
		log.info("Delete income received: " + id);
		incomeAdapter.deleteIncome(user, id);
		return "redirect:../list";
	}
}
