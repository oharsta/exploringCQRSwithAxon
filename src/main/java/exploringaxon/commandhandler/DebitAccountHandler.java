package exploringaxon.commandhandler;

import exploringaxon.api.command.DebitAccountCommand;
import exploringaxon.model.Account;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dadepo Aderemi.
 */
@Component
public class DebitAccountHandler {

    private Repository repository;

    @Autowired
    public DebitAccountHandler(Repository repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommandCommand){
        Account accountToDebit = (Account) repository.load(debitAccountCommandCommand.getAccount());
        accountToDebit.debit(debitAccountCommandCommand.getAmount());
    }
}
