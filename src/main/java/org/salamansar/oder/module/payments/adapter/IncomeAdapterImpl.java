package org.salamansar.oder.module.payments.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.salamansar.oder.core.adapter.Adapter;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.IncomeService;
import org.salamansar.oder.module.payments.dto.IncomeDto;
import org.salamansar.oder.module.payments.dto.QuarterIncomeDto;
import org.salamansar.oder.module.payments.mapper.IncomeMapper;
import org.salamansar.oder.module.payments.mapper.QuarterIncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Salamansar
 */
@Adapter
public class IncomeAdapterImpl implements IncomeAdapter {
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private IncomeMapper incomeMapper;
	@Autowired
	private QuarterIncomeMapper quarterIncomeMapper;

	@Override
	public List<IncomeDto> getAllIncomes(User user) {
		List<Income> domains = incomeService.getAllIncomes(user);
		return domains.stream()
				.map(incomeMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<QuarterIncomeDto> getAllYearIncomes(User user) {
		List<QuarterIncome> domains = incomeService.findAllQuarterIncomes(user, false);
		return domains.stream()
				.map(quarterIncomeMapper::mapToDto)
				.sorted((a,b) -> b.getPeriod().compareTo(a.getPeriod()))
				.collect(Collectors.toList());
	}
	
	@Override
	public Long addIncome(User user, IncomeDto dto) {
		Income domain = incomeMapper.mapFromDto(dto);
		domain.setUser(user);
		return incomeService.addIncome(domain);
	}

	@Override
	public void editIncome(User user, IncomeDto dto) {
		//todo: check rights for updating
		Income domain = incomeMapper.mapFromDto(dto);
		domain.setUser(user);
		incomeService.updateIncome(domain);
	}
	
	@Override
	public IncomeDto getIncome(User user, Long id) {
		Optional<Income> storedIncome = incomeService.getIncome(id);
		return storedIncome.map(income -> {
				//todo: throw illegal access exception
				return incomeMapper.mapToDto(income);
		}).orElse(null);
	}

	@Override
	public void deleteIncome(User user, Long id) {
		//todo: check rights for deletion
		incomeService.deleteIncome(id);
	}
	
}
