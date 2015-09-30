package exploringaxon.api.event;

/**
 * Event Class that communicates that an account has been debited
 *
 * Created by Dadepo Aderemi.
 */
public class AccountDebitedEvent {
    private final String accountNo;
    private final Double amountDebited;
    private final Double balance;

    public AccountDebitedEvent(String accountNo, Double amountDebited, Double balance) {
        this.accountNo = accountNo;
        this.amountDebited = amountDebited;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Double getAmountDebited() {
        return amountDebited;
    }

    public Double getBalance() {
        return balance;
    }
}
