package org.salamansar.oder.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
@Entity
public class Income implements HasId, Serializable {
    @Id
    private Long id;
    @Column(nullable = false)
    private Date incomeDate;
    @Column(nullable = false)
    private BigDecimal amount;
    private String description;
    private Integer documentNumber;
    
}
