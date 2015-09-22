package exploringaxon;

import exploringaxon.api.command.DebitAccount;
import exploringaxon.api.command.DebitAccountHandler;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Dadepo Aderemi.
 */
@Configuration
public class AppConfiguration {

    @Bean
    public SimpleCommandBus commandBus() {
        SimpleCommandBus simpleCommandBus = new SimpleCommandBus();
        // This manually subscribes the command handler: DebitAccountHandler to the commandbus
        simpleCommandBus.subscribe(DebitAccount.class.getName(), new DebitAccountHandler());
        return simpleCommandBus;
    }


    @Bean
    AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        /**
         * The AnnotationCommandHandlerBeanPostProcessor finds all beans that has @CommandHandler
         * and subscribed them to the commandBus with the first argument of the method being the
         * the command type the method will be subscribed to.
         */
        AnnotationCommandHandlerBeanPostProcessor handler = new AnnotationCommandHandlerBeanPostProcessor();
        handler.setCommandBus(commandBus());
        return handler;
    }

    @Bean
    public DefaultCommandGateway commandGateway() {
        return new DefaultCommandGateway(commandBus());
    }
}
