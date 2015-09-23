package exploringaxon.api.command;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.springframework.stereotype.Component;

/**
 * Created by Dadepo Aderemi.
 */
@Component
public class CreditAccountHandler {

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommandCommand){

        System.out.println("I can handle the creditAccount command: "
                                   + "Account to credit: " + creditAccountCommandCommand.getAccount()
                                   + " Amount to credit with: "+ creditAccountCommandCommand.getAmount());
    }
}
