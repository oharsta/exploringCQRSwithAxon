package exploringaxon.web;

import exploringaxon.api.command.CreditAccount;
import exploringaxon.api.command.DebitAccount;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Dadepo Aderemi.
 */
@Controller
public class IndexController {

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "dadepo");
        return "index";
    }


    @RequestMapping("/debit")
    @ResponseBody
    public void doDebit(@RequestParam("acc") String accountNumber, @RequestParam("amount") double amount) {
        DebitAccount debitAccountCommand = new DebitAccount(accountNumber, amount);
        commandGateway.send(debitAccountCommand);
    }

    @RequestMapping("/credit")
    @ResponseBody
    public void doCredit(@RequestParam("acc") String accountNumber, @RequestParam("amount") double amount) {
        CreditAccount creditAccountCommand = new CreditAccount(accountNumber, amount);
        commandGateway.send(creditAccountCommand);
    }
}
