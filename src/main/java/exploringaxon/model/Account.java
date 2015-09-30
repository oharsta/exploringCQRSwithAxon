package exploringaxon.model;

import exploringaxon.api.event.AccountCreditedEvent;
import exploringaxon.api.event.AccountDebitedEvent;
import org.axonframework.domain.AbstractAggregateRoot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity that models the Account
 *
 * Created by Dadepo Aderemi.
 */
@Entity
public class Account extends AbstractAggregateRoot {

    @Id
    private String id;

    @Column
    private Double balance;

    public Account() {
    }

    public Account(String id) {
        this.id = id;
        this.balance = 0.0d;
    }

    /**
     * Business Logic
     * Cannot debit with a negative amount
     * Cannot debit with more than a million amount (You laundering money?)
     * @param debitAmount
     */
    public void debit(Double debitAmount) {

        if (Double.compare(debitAmount, 0.0d) > 0 &&
                this.balance - debitAmount > -1) {
            this.balance -= debitAmount;

            /**
             * A change in state of the Account has occurred which can be represented by an to an event: i.e.
             * the account has been debited so an AccountDebitedEvent is created and registered.
             *
             * When the repository stores this change in state, it will also publish the AccountDebitedEvent
             * to the outside world.
             */
            AccountDebitedEvent accountDebitedEvent = new AccountDebitedEvent(this.id, debitAmount, this.balance);
            registerEvent(accountDebitedEvent);

        } else {
            throw new IllegalArgumentException("Cannot debit with the amount");
        }

    }

    /**
     * Business Logic
     * Cannot credit with a negative amount
     * Cannot credit with amount that leaves the account balance in a negative state
     * @param creditAmount
     */
    public void credit(Double creditAmount) {
        if (Double.compare(creditAmount, 0.0d) > 0 &&
                Double.compare(creditAmount, 1000000) < 0) {
            this.balance += creditAmount;

            /**
             * A change in state of the Account has occurred which can be represented by an to an event: i.e.
             * the account has been credited so an AccountCreditedEvent is created and registered.
             *
             * When the repository stores this change in state, it will also publish the AccountCreditedEvent
             * to the outside world.
             */
            AccountCreditedEvent accountCreditedEvent = new AccountCreditedEvent(this.id, creditAmount, this.balance);
            registerEvent(accountCreditedEvent);
        } else {
            throw new IllegalArgumentException("Cannot credit with the amount");
        }
    }

    public Double getBalance() {
        return balance;
    }

    public void setIdentifier(String id) {
        this.id = id;
    }

    @Override
    public Object getIdentifier() {
        return id;
    }
}
