import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by Vineeth on 2/6/2015.
 */
public class ProgramServer extends Application<Configuration> {
    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {

    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        final GetHistoricalWeatherData getHist = new GetHistoricalWeatherData("29.6520", "-82.3250");
        environment.jersey().register(getHist);

        final GetWeather getWeatherData = new GetWeather("29.6520", "-82.3250");
        environment.jersey().register(getWeatherData);
    }

    public static void main(String[] args) throws Exception {
        new ProgramServer().run(args);
    }
}
