 package fr.unice.polytech.card.business;

 import arquillian.AbstractArquillianTest;
 import fr.unice.polytech.card.interfaces.CardFinder;
 import fr.unice.polytech.entities.Card;
 import fr.unice.polytech.entities.Pass;
 import fr.unice.polytech.utils.CardType;
 import fr.unice.polytech.utils.PassType;
 import org.jboss.arquillian.junit.Arquillian;
 import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
 import org.jboss.arquillian.transaction.api.annotation.Transactional;
 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;
 import org.junit.runner.RunWith;

 import javax.ejb.EJB;
 import javax.inject.Inject;
 import javax.persistence.EntityManager;
 import javax.persistence.PersistenceContext;
 import javax.transaction.UserTransaction;

 import java.util.List;
 import java.util.Optional;

 import static org.junit.Assert.assertEquals;
 import static org.junit.Assert.assertTrue;

 @RunWith(Arquillian.class)
 @Transactional(TransactionMode.COMMIT)
 public class PassTest extends AbstractArquillianTest {


     @EJB
     private CardFinder cardFinder;

     @Inject
     private UserTransaction utx;

     @PersistenceContext(unitName = "castexski_customer_persistence_unit")
     private EntityManager manager;

     Pass pass;

     @Before
     public void setUpContext() {
         pass = new Pass();

     }

     @After
     public void cleaningUp() throws Exception {
         utx.begin();
         pass = manager.merge(pass);
         manager.remove(pass);
         pass = null;
         utx.commit();
     }

     @Test
     public void putPassTest(){
         // pass doesn't exit before in the database
         pass.setPassType(PassType.CLASSIC);
         cardFinder.putPass(pass);
         String idPass = pass.getId();
         Pass stored = manager.find(Pass.class, idPass);
         assertEquals(pass, stored);
         assertEquals(pass.getPassType(), stored.getPassType());

         // change pass type and store it in database => the database is updated
         pass.setPassType(PassType.WEEK);
         cardFinder.putPass(pass);
         stored = manager.find(Pass.class, idPass);
         assertEquals(pass, stored);
         assertEquals(pass.getPassType(), stored.getPassType());

     }


     @Test
     public void getNonExistingPassByIdTest(){
         assertEquals(cardFinder.getPassById("0000"), Optional.empty());
     }

     @Test
     public void getAllPassTest(){
         pass.setPassType(PassType.CLASSIC);
         Pass pass2 = new Pass();
         pass2.setPassType(PassType.CLASSIC);
         cardFinder.putPass(pass);
         cardFinder.putPass(pass2);
         List<Pass> passes = cardFinder.getAllPasses();
         assertTrue(passes.contains(pass));
         assertTrue(passes.contains(pass2));
     }




 }
