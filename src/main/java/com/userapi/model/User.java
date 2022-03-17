package com.userapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * User entity class
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user",
  indexes = {
     @Index(name = "email_idx", columnList = "email", unique = true)
})
public class User {

    /**
     * id of the entity
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    /**
     * userName
     */
    @Column(name = "username", nullable = false)
    private String userName;

    /**
     * email address
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * monthly Income
     */
    @Column(name = "monthly_income", nullable = false)
    private BigDecimal monthlyIncome;

    /**
     * monthly Expense
     */
    @Column(name = "monthly_expense", nullable = false)
    private BigDecimal monthlyExpense;

    /**
     * oneToMany relation with account entity
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    //@JsonManagedReference
    @JsonIgnore
    private List<Account> accounts;

}
