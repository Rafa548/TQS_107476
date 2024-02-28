package integration;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolverService;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AddressResolverIT {

    private AddressResolverService resolver;

    @BeforeEach
    public void init(){
        ISimpleHttpClient httpClient = new TqsBasicHttpClient();
        resolver = new AddressResolverService(httpClient);
    }


    //@Disabled
    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);
        //return
        Address expected = new Address( "Avenida da Universidade", "Aveiro","3810-489", "");

        assertTrue( result.isPresent());
        assertEquals( expected, result.get());

    }

    //@Disabled
    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {
        Optional<Address> result = resolver.findAddressForLocation( -361,-361);
        assertFalse( result.isPresent());
    }

}
