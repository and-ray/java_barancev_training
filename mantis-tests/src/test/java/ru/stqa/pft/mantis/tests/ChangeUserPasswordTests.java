package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.TestBase;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangeUserPasswordTests extends TestBase {

        @BeforeMethod
        public void startMailServer(){
            app.mail().start();
        }

        @Test
        public static void testUpdateUserPassword() throws IOException, MessagingException, InterruptedException {
            String userName = "user1535660128034";
            String newPassword = "lololo";
            app.mantisUsage().start("administrator", "root");
            app.mantisUsage().resetUserPassword(userName);
            //app. считать мыло из БД
            // Thread.sleep(2000);
            String email = app.mantisUsage().getEmail(userName);
            List<MailMessage> mailMessages = app.mail().waitForMail(1, 10 * 1000);
            String confirmationLink = findConfirmationLink(mailMessages, email);
            //app.mantisUsage().start(userName, email);
            app.registration().finish(confirmationLink, newPassword);
            assertTrue(app.newSession().login(userName, newPassword));
        }

        private static String findConfirmationLink(List<MailMessage> mailMessages, String email) {
            MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
            VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
            //System.out.println("что дает метод поиска ссылки: " + regex.getText(mailMessage.text));
            return regex.getText(mailMessage.text);
        }

        @AfterMethod(alwaysRun = true)
        public void stopMailServer(){
            app.mail().stop();
        }
    }
