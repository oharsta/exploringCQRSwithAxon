package exploringaxon.api.command;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.springframework.stereotype.Component;

/**
 * Created by Dadepo Aderemi.
 */
@Component
public class CreditAccountHandler {

    @CommandHandler
    public void handle(CreditAccount creditAccountCommand){

        System.out.println("I can handle the creditAccount command: "
                                   + "Account to credit: " + creditAccountCommand.getAccount()
                                   + " Amount to credit with: "+ creditAccountCommand.getAmount());
    }
}
