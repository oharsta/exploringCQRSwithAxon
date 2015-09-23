package exploringaxon.api.command;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.springframework.stereotype.Component;

/**
 * Created by Dadepo Aderemi.
 */
@Component
public class DebitAccountHandler {

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommandCommand, CommandMessage<DebitAccountCommand> message){

        System.out.println("I can handle the debitAccount command: "
                                   + "Account to debit: " + debitAccountCommandCommand.getAccount()
                                   + " Amount to debit with: "+ debitAccountCommandCommand.getAmount());
    }
}
