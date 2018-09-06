package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.TestBase;
import ru.stqa.pft.mantis.model.MailMessage;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangeUserPasswordTests extends TestBase {

        @BeforeMethod
        public void startMailServer(){
            app.mail().start();
        }

        @Test
        public static void testUpdateUserPassword() throws IOException {
            String userName = null;
            String newPassword = "lololo";
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bugtracker?user=root&password=&serverTimezone=UTC");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select username from mantis_user_table");
                while(rs.next()){
                    userName = rs.getString("username");
                    if (!userName.equals("administrator")){break;}
                }

                rs.close();
                st.close();
                conn.close();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            //System.out.println("userName = "+ userName);
            app.mantisUsage().start("administrator", "root");
            app.mantisUsage().resetUserPassword(userName);
            String email = app.mantisUsage().getEmail(userName);
            List<MailMessage> mailMessages = app.mail().waitForMail(1, 10 * 1000);
            String confirmationLink = findConfirmationLink(mailMessages, email);
            app.registration().finish(confirmationLink, newPassword);
            assertTrue(app.newSession().login(userName, newPassword));
        }

        private static String findConfirmationLink(List<MailMessage> mailMessages, String email) {
            MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
            VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
            return regex.getText(mailMessage.text);
        }

        @AfterMethod(alwaysRun = true)
        public void stopMailServer(){
            app.mail().stop();
        }
    }
