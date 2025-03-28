package es.neesis.soapclient;

import es.neesis.soapclient.client.UserClient;
import es.neesis.soapclient.config.UserClientConfig;
import es.neesis.soapclient.ws.user.GetUserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserClientConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserClientLiveTest {

    @Autowired
    private UserClient userClient;

    @Test
    public void whenSendRequest_thenRecieveValidResponse() {
        GetUserResponse testUser = userClient.getUser(1);
        assertEquals(testUser.getUser().getUsername(), "user1");
    }

    @Test
    public void whenSendRequestAuthenticate_thenRecieveValidResponse(){
        GetUserResponse testUser = userClient.authenticate(1, "user1", "pwd");
        assertEquals(testUser.getUser().getUsername(), "user1");
        assertEquals(testUser.getUser().getPassword(), "pwd");
    }

    @Test
    public void whenSendRequestAuthenticate_thenRecieveInvalidPasswordResponse(){
        GetUserResponse testUser = userClient.authenticate(1, "user1", "pwdddddd");
        assertEquals(testUser.getUser().getUsername(), "user1");
        //assertEquals(testUser.getUser().getPassword(), "pwd");
    }

    @Test
    public void whenSendRequestAuthenticate_thenRecieveInvalidUserResponse(){
        GetUserResponse testUser = userClient.authenticate(2, "user2", "pwd");
        //assertEquals(testUser.getUser().getUsername(), "user1");
        //assertEquals(testUser.getUser().getPassword(), "pwd");
    }

    @Test
    public void whenSendRequestAuthenticate_thenRecieveExpiredDateResponse(){
        GetUserResponse testUser = userClient.authenticate(1, "user1", "pwd");
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = currentDate.format(formatter);
        assertEquals(testUser.getUser().getExpirationDate(),formattedDate);


        //assertEquals(testUser.getUser().getPassword(), "pwd");
    }
}
