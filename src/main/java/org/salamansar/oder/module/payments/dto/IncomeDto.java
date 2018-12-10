package org.salamansar.oder.module.payments.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Salamansar
 */
@Data
public class IncomeDto { //todo: use mapped dto instead domain
	private Long id;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date incomeDate;
	private BigDecimal amount;
	private String description;
	private Integer documentNumber;
}
