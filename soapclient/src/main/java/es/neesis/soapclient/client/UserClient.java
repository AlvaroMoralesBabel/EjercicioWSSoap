package es.neesis.soapclient.client;

import es.neesis.soapclient.ws.user.GetUserRequest;
import es.neesis.soapclient.ws.user.GetUserResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.Base64;

public class UserClient extends WebServiceGatewaySupport {

    public GetUserResponse getUser(int id) {

        GetUserRequest request = new GetUserRequest();
        request.setId(id);

        return (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    public GetUserResponse authenticate(int id, String username, String password){
        /*
        Cliente de login de usuario:
        a partir de un nombre de usuario y contraseña en texto plano encriptará esta segunda en base 64
        y consumirá el webservice de authenticate.
         */
        GetUserRequest request = new GetUserRequest();
        request.setId(id);
        request.setUsername(username);

        request.setPassword(Base64.getEncoder().encodeToString(password.getBytes()));

        return (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
