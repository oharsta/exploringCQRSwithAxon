package exploringaxon.api.command;

/**
 * Created by Dadepo Aderemi.
 */
public class CreditAccountCommand {

    private final String account;
    private final Double amount;

    public CreditAccountCommand(String account, Double amount) {
        this.account = account;
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public Double getAmount() {
        return amount;
    }
}
