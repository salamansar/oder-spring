package org.salamansar.oder.core.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
@Entity
@Table(name = "oder_user")
public class User implements HasId, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_user")
	@SequenceGenerator(name = "gen_user", sequenceName = "seq_user_id", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private String passwordHash;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @OneToMany
    private List<Income> incomes;
}
