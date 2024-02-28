package geocoding;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @InjectMocks // always inject mocks into service
    AddressResolverService resolver;

    @Mock //mocking the http client
    ISimpleHttpClient httpClient;

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        when(httpClient.doHttpGet(anyString())).thenReturn("{\n" +
                "  \"info\": {\n" +
                "    \"statuscode\": 0,\n" +
                "    \"copyright\": {\n" +
                "      \"text\": \"© 2024 MapQuest, Inc.\",\n" +
                "      \"imageUrl\": \"https://api.mqcdn.com/res/mqlogo.gif\",\n" +
                "      \"imageAltText\": \"© 2024 MapQuest, Inc.\"\n" +
                "    },\n" +
                "    \"messages\": []\n" +
                "  },\n" +
                "  \"options\": {\n" +
                "    \"maxResults\": 1,\n" +
                "    \"thumbMaps\": true,\n" +
                "    \"ignoreLatLngInput\": false\n" +
                "  },\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"providedLocation\": {\n" +
                "        \"latLng\": {\n" +
                "          \"lat\": 30.333472,\n" +
                "          \"lng\": -81.470448\n" +
                "        }\n" +
                "      },\n" +
                "      \"locations\": [\n" +
                "        {\n" +
                "          \"street\": \"Avenida da Universidade\",\n" +

                "          \"adminArea6\": \"\",\n" +
                "          \"adminArea6Type\": \"Neighborhood\",\n" +
                "          \"adminArea5\": \"Aveiro\",\n" +
                "          \"adminArea5Type\": \"City\",\n" +
                "          \"adminArea4\": \"Duval\",\n" +
                "          \"adminArea4Type\": \"County\",\n" +
                "          \"adminArea3\": \"FL\",\n" +
                "          \"adminArea3Type\": \"State\",\n" +
                "          \"adminArea1\": \"US\",\n" +
                "          \"adminArea1Type\": \"Country\",\n" +
                "          \"postalCode\": \"3810-489\",\n" +
                "          \"geocodeQualityCode\": \"L1AAA\",\n" +
                "          \"geocodeQuality\": \"ADDRESS\",\n" +
                "          \"dragPoint\": false,\n" +
                "          \"sideOfStreet\": \"R\",\n" +
                "          \"linkId\": \"0\",\n" +
                "          \"unknownInput\": \"\",\n" +
                "          \"type\": \"s\",\n" +
                "          \"latLng\": {\n" +
                "            \"lat\": 30.33472,\n" +
                "            \"lng\": -81.470448\n" +
                "          },\n" +
                "          \"displayLatLng\": {\n" +
                "            \"lat\": 30.333472,\n" +
                "            \"lng\": -81.470448\n" +
                "          },\n" +
                "          \"mapUrl\": \"https://www.mapquestapi.com/staticmap/v4/getmap?key=KEY&type=map&size=225,160&pois=purple-1,30.3334721,-81.4704483,0,0,|&center=30.3334721,-81.4704483&zoom=15&rand=-553163060\",\n" +
                "          \"nearestIntersection\": {\n" +
                "            \"streetDisplayName\": \"Posey Cir\",\n" +
                "            \"distanceMeters\": \"851755.1608527573\",\n" +
                "            \"latLng\": {\n" +
                "              \"longitude\": -87.523761,\n" +
                "              \"latitude\": 35.013434\n" +
                "            },\n" +
                "            \"label\": \"Danley Rd & Posey Cir\"\n" +
                "          },\n" +
                "          \"roadMetadata\": {\n" +
                "            \"speedLimitUnits\": \"mph\",\n" +
                "            \"tollRoad\": null,\n" +
                "            \"speedLimit\": 40\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}");


        // working
        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);

        //return
        Address expected = new Address( "Avenida da Universidade", "Aveiro","3810-489", "");

        assertTrue( result.isPresent());
        assertEquals( expected, result.get());
    }

    @Test

    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {
        
        when(httpClient.doHttpGet(anyString())).thenReturn("{\n" +
                "  \"info\": {\n" +
                "    \"statuscode\": 400,\n" +
                "    \"copyright\": {\n" +
                "      \"text\": \"© 2024 MapQuest, Inc.\",\n" +
                "      \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
                "      \"imageAltText\": \"© 2024 MapQuest, Inc.\"\n" +
                "    },\n" +
                "    \"messages\": [\n" +
                "      \"Illegal argument from request: Invalid LatLng specified.\"\n" +
                "    ]\n" +
                "  },\n" +
                "  \"options\": {\n" +
                "    \"maxResults\": 1,\n" +
                "    \"ignoreLatLngInput\": false\n" +
                "  },\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"providedLocation\": {},\n" +
                "      \"locations\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}");

        Optional<Address> result = resolver.findAddressForLocation( -361,-361);
        // verify no valid result
        assertFalse( result.isPresent());

    }
}