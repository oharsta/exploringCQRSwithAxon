package exploringaxon;

import exploringaxon.model.Account;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by Dadepo Aderemi.
 */
@Configuration
public class AppConfiguration {

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
     * An event sourcing implementation needs a place to store events. i.e. The event Store.
     * In our use case we will be storing our events in a file system, so we configure
     * the FileSystemEventStore as our EventStore implementation
     *
     * It should be noted that Axon allows storing the events
     * in other persistent mechanism...jdbc, jpa etc
     *
     * @return the {@link EventStore}
     */
    @Bean
    public EventStore eventStore() {
        EventStore eventStore = new FileSystemEventStore(new SimpleEventFileResolver(new File("./events")));
        return eventStore;
    }

    /**
     * Our aggregate root is now created from stream of events and not from a representation in a persistent mechanism,
     * thus we need a repository that can handle the retrieving of our aggregate root from the stream of events.
     *
     * We configure the EventSourcingRepository which does exactly this. We supply it with the event store
     * @return {@link EventSourcingRepository}
     */
    @Bean
    public EventSourcingRepository eventSourcingRepository() {
        EventSourcingRepository eventSourcingRepository = new EventSourcingRepository(Account.class, eventStore());
        eventSourcingRepository.setEventBus(eventBus());
        return eventSourcingRepository;
    }
}
