package exploringaxon;

import exploringaxon.model.Account;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.repository.GenericJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Dadepo Aderemi.
 */
@Configuration
public class AppConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public SimpleCommandBus commandBus() {
        SimpleCommandBus simpleCommandBus = new SimpleCommandBus();
        return simpleCommandBus;
    }

    /**
     * The simple command bus, an implementation of an EventBus
     * mostly appropriate in a single JVM, single threaded use case.
     * @return the {@link SimpleEventBus}
     */
    @Bean
    public SimpleEventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        /**
         * The AnnotationCommandHandlerBeanPostProcessor finds all beans that has methods that has @CommandHandler
         * and subscribed them to the commandBus with the first argument of the method being the
         * the command type the method will be subscribed to.
         */
        AnnotationCommandHandlerBeanPostProcessor handler = new AnnotationCommandHandlerBeanPostProcessor();
        handler.setCommandBus(commandBus());
        return handler;
    }

    @Bean
    AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
        /**
         * The AnnotationEventListenerBeanPostProcessor finds all beans that has methods annotated with @EventHandler
         * and subscribe them to the eventbus.
         */
        AnnotationEventListenerBeanPostProcessor listener = new AnnotationEventListenerBeanPostProcessor();
        listener.setEventBus(eventBus());
        return listener;
    }

    @Bean
    public DefaultCommandGateway commandGateway() {
        return new DefaultCommandGateway(commandBus());
    }

    /**
     * Configures a GenericJpaRepository as a Spring Bean. The Repository would be used to access
     * the Account entity.
     *
     */
    @Bean
    public GenericJpaRepository genericJpaRepository() {
        SimpleEntityManagerProvider entityManagerProvider = new SimpleEntityManagerProvider(entityManager);
        GenericJpaRepository genericJpaRepository = new GenericJpaRepository(entityManagerProvider, Account.class);
        /**
         * Configuring the repository with an event bus which allows the repository
         * to be able to publish domain events
         */
        genericJpaRepository.setEventBus(eventBus());
        return genericJpaRepository;
    }
}
