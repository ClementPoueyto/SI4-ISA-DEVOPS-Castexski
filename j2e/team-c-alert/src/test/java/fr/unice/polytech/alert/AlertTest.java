package fr.unice.polytech.alert;

import fr.unice.polytech.alert.interfaces.MailSender;
import fr.unice.polytech.alert.utils.MailAPI;
import fr.unice.polytech.analytics.interfaces.AnalyticsFinder;
import fr.unice.polytech.exceptions.ExternalPartnerException;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class AlertTest extends AbstractCastexSkiTest {

    @EJB
    MailSender mailer;

    @EJB
    AnalyticsFinder analyticsFinder;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpContext() throws ExternalPartnerException {

    }

    @Test
    public void sendMailMocked() {
        MailAPI mocked = mock(MailAPI.class);
        mailer.useMailerAPIReference(mocked);
        when(mocked.sendMailToMayor()).thenReturn(true);
        assertTrue(mailer.dailyMail());
    }

    @Test
    public void sendMail() {
        MailAPI mailAPI = new MailAPI(analyticsFinder);
        mailer.useMailerAPIReference(mailAPI);
        assertTrue(mailer.dailyMail());
    }
}