package es.neesis.soapclient.client;

import es.neesis.soapclient.ws.user.GetUserRequest;
import es.neesis.soapclient.ws.user.GetUserResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class UserClient extends WebServiceGatewaySupport {

    public GetUserResponse getUser(int id) {
        /*
        Cliente de login de usuario:
        a partir de un nombre de usuario y contraseña en texto plano encriptará esta segunda en base 64
        y consumirá el webservice de authenticate.
        Una vez reciba respuesta, si el login es correcto, realizará una petición al
        WS público de numbersToWords al que le pasará como parámetro el la suma de los
        valores numéricos ascii del email del usuario, y una vez reciba la respuesta la imprimirá por consola (O en un log si os diese tiempo)
         */
        GetUserRequest request = new GetUserRequest();
        request.setId(id);

        return (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
