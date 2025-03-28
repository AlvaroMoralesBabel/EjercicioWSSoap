package es.neesis.soapserver.endpoint;

import es.neesis.soapserver.repository.FakeUserDB;
import es.neesis.soapserver.ws.user.GetUserRequest;
import es.neesis.soapserver.ws.user.GetUserResponse;
import es.neesis.soapserver.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Endpoint
public class AuthenticateEndpoint {
    private static final String NAMESPACE_URI = "http://www.neesis.es/soapserver/ws/user";

    private final FakeUserDB userRepository;

    @Autowired
    public AuthenticateEndpoint(FakeUserDB userRepository) {
        this.userRepository = userRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {

        User user = userRepository.getUser(request.getId());

        GetUserResponse response = new GetUserResponse();

        String password = new String(Base64.getDecoder().decode(request.getPassword()));

        String userPassword = user.getPassword();

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Definir el formato de la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // Convertir la fecha a String
        String fechaString = fechaActual.format(formatter);

        if (password.equals(userPassword) && user.getUsername() != null)
        {
            response.setCodigo("OK");
            response.setMensajeError("OK");
            user.setLastLoginDate(fechaString);
        }

        // si la contraseña no es válida
        else if (!password.equals(userPassword))
        {
            response.setCodigo("KO");
            response.setMensajeError("Contraseña incorrecta");
        }

        // si el nombre de usuario no existe
        else if (user.getUsername() == null)
        {
            response.setCodigo("KO");
            response.setMensajeError("El usuario no existe");
        }

        // si la contraseña ha expirado
        else if (user.getExpirationDate().compareTo(fechaString) < 0)
        {
            response.setCodigo("KO");
            response.setMensajeError("La contraseña ha expirado");
        }

        response.setUser(user);

        return response;
    }
}
