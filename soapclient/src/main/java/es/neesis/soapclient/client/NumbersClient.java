package es.neesis.soapclient.client;

import es.neesis.soapclient.ws.user.GetUserRequest;
import es.neesis.soapclient.ws.user.GetUserResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class NumbersClient extends WebServiceGatewaySupport {

    public GetUserResponse numbersToWords(double suma)
    {
        /* TODO
        URL del wsdl público de numbersToWords:
        https://www.dataaccess.com/webservicesserver/NumberConversion.wso?WSDL
         */
        GetUserRequest request = new GetUserRequest();


        return (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
