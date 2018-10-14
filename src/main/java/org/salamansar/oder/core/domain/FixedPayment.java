package org.salamansar.oder.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import org.salamansar.oder.core.domain.converter.TaxCategoryConverter;

/**
 *
 * @author Salamansar
 */
@Data
@Entity
@Table(	name = "fixed_payment", 
		uniqueConstraints = @UniqueConstraint(columnNames = {"payment_year", "id_category"})
)
public class FixedPayment implements HasId, Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "payment_year", nullable = false)
	private Integer year;
	@Column(name = "payment_value", nullable = false)
	private BigDecimal value;
	@Column(name = "id_category", nullable = false)
	@Convert(converter = TaxCategoryConverter.class)
	private TaxCategory category;
}
