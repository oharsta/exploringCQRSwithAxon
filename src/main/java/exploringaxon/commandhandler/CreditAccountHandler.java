package exploringaxon.commandhandler;

import exploringaxon.api.command.CreditAccountCommand;
import exploringaxon.model.Account;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by Dadepo Aderemi.
 */
@Component
public class CreditAccountHandler {

    private Repository repository;

    @Autowired
    public CreditAccountHandler(Repository repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommandCommand){
        Account accountToCredit = (Account) repository.load(creditAccountCommandCommand.getAccount());
        accountToCredit.credit(creditAccountCommandCommand.getAmount());
    }

}
