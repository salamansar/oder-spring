package org.salamansar.oder.module.payments.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.salamansar.oder.core.domain.PaymentPeriod;

/**
 *
 * @author Salamansar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuarterIncomeDto {
	private PaymentPeriod period;
	private BigDecimal incomeAmount;
}
