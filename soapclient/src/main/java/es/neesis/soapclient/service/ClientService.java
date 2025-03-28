package es.neesis.soapclient.service;

import ch.qos.logback.core.net.server.Client;
import es.neesis.soapclient.client.NumbersClient;
import es.neesis.soapclient.client.UserClient;
import es.neesis.soapclient.ws.user.GetUserResponse;
import es.neesis.soapclient.ws.user.User;

import java.util.Objects;

public class ClientService {

    private UserClient userClient;
    private NumbersClient numbersClient;

    public ClientService(UserClient userClient, NumbersClient numbersClient)
    {
        this.userClient = userClient;
        this.numbersClient = numbersClient;
    }

    public GetUserResponse servicioCliente(int id, String username, String password)
    {
        /*
        Si el login es correcto, realizará una petición al
        WS público de numbersToWords al que le pasará como parámetro la suma de los
        valores numéricos ascii del email del usuario,
        y una vez reciba la respuesta la imprimirá por consola (O en un log si os diese tiempo)
        */
        GetUserResponse respuesta = userClient.authenticate(id, username, password);

        if (Objects.equals(respuesta.getCodigo(), "OK"))
        {
            User user = respuesta.getUser();
            // Suma = la suma de los valores numéricos ascii del email del usuario
            Double suma = null;
            numbersClient.numbersToWords(suma);

            System.out.println("Respuesta obtenida: ");
        }
    }
}
