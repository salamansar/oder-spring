package org.salamansar.oder.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
@Entity
public class Income implements HasId, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_income")
	@SequenceGenerator(name = "gen_income", sequenceName = "seq_income_id", allocationSize = 1)
	private Long id;
	@Column(nullable = false, name = "income_date")
	private LocalDate incomeDate;
	@Column(nullable = false)
	private BigDecimal amount;
	private String description;
	@Column(name = "document_number")
	private Integer documentNumber;
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

}
