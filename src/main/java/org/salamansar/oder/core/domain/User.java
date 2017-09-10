package org.salamansar.oder.core.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
@Entity
public class User implements HasId, Serializable {
    @Id
    private Long id;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String passwordHash;
    private String lastName;
    private String firstName;
    private String middleName;
}
