import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vineeth on 2/6/2015.
 */

@Path("/get-historical-weather")
@Produces(MediaType.APPLICATION_JSON)
public class GetHistoricalWeatherData {

    private final String defaultLat;
    private final String defaultLong;

    public GetHistoricalWeatherData(String defaultLat, String defaultLong) {
        this.defaultLat = defaultLat;
        this.defaultLong = defaultLong;
    }

    @GET
    public String getHistoricalData(@QueryParam("lat") Optional<String> latitude,
                                    @QueryParam("long") Optional<String> longitude){

        final String lat = latitude.or(defaultLat);
        final String lon = longitude.or(defaultLong);

        return getHistoricalDataFromAPI(lat, lon);
    }

    private String getHistoricalDataFromAPI(String latitude, String longitude){
        try {
            String getWeatherByCoordinates = "http://api.openweathermap.org/data/2.5/forecast/daily?lat="+latitude+"&lon="+longitude+"&cnt=10&mode=json&units=metric";
            URL url = new URL(getWeatherByCoordinates);
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", "41838688d7ca5218003ad5c7befa0682");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            return json.toString();
        }catch(Exception e){
            return null;
        }
    }
}
