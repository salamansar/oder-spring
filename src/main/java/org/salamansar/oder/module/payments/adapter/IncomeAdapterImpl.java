package org.salamansar.oder.module.payments.adapter;

import java.util.List;
import java.util.stream.Collectors;
import org.salamansar.oder.core.adapter.Adapter;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.IncomeService;
import org.salamansar.oder.module.payments.dto.IncomeDto;
import org.salamansar.oder.module.payments.mapper.IncomeMapper;
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

	@Override
	public List<IncomeDto> getAllIncomes(User user) {
		List<Income> domains = incomeService.getAllIncomes(user);
		return domains.stream()
				.map(incomeMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public Long addIncome(User user, IncomeDto dto) {
		Income domain = incomeMapper.mapFromDto(dto);
		domain.setUser(user);
		return incomeService.addIncome(domain);
	}
	
}
