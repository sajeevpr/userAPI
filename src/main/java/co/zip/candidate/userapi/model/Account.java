package co.zip.candidate.userapi.model;

import co.zip.candidate.userapi.constants.AccountType;
import co.zip.candidate.userapi.constants.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Account entity class
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account",
        indexes = {
        @Index(name = "account_idx", columnList = "account_type, account_name", unique = true)
})
public class Account {

    /**
     * id of the entity
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "account_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    /**
     * accountType
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    /**
     * accountName
     */
    @Column(name = "account_name", nullable = false)
    private String accountName;

    /**
     * balance
     */
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    /**
     * currency
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currency;

    /**
     * ManyToOne relation with user entity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    /**
     * Transient userId for returning to the API
     */
    @Transient
    private UUID userId;

    /**
     * To get UserId for Jackson JSON API
     * @return userId
     */
    public String getUserId() {
        return user.getId().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
