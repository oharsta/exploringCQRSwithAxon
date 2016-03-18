package exploringaxon;

import exploringaxon.model.Account;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.unitofwork.CurrentUnitOfWork;
import org.axonframework.unitofwork.DefaultUnitOfWork;
import org.axonframework.unitofwork.UnitOfWork;
import org.junit.Test;

public class RepositoryTest {

  @Test
  public void testRepo() {
    CurrentUnitOfWork.set(DefaultUnitOfWork.startAndGet());

    AppConfiguration conf = new AppConfiguration();
    EventSourcingRepository<Account> repository = conf.eventSourcingRepository();
    Account accountToCredit = repository.load("acc-two");
//    for (int i = 0; i < 100000; i++) {
//      accountToCredit.credit(new Double(1d));
//    }
//    CurrentUnitOfWork.commit();
  }
}
