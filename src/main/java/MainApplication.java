import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import models.*;
import resources.*;
import services.AuthenticationService;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import services.ContactService;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.EnumSet;


public class MainApplication extends Application<MainConfiguration> {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new MainApplication().run(new String[] {"server", "config.yml"});
    }

    /**
     * @author Mike van Es
     */
    @Override
    public String getName() {
        return "starcourt";
    }

    /**
     *  @author Mike van Es
     */
    @Override
    public void initialize(final Bootstrap<MainConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<MainConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(MainConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) throws UnsupportedEncodingException, SQLException {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "*");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        final ProductResource productResource = new ProductResource();
        final OrderResource orderResource = new OrderResource();
        final AuthResource authResource = new AuthResource();
        final ContactResource contactResource = new ContactResource();
        final UserResource userResource = new UserResource();

        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Principal.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        // code to register module
        environment.jersey().register(productResource);
        environment.jersey().register(orderResource);
        environment.jersey().register(authResource);
        environment.jersey().register(contactResource);
        environment.jersey().register(userResource);


        //toevoegen van de OAuth2 authenticator
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<UserModel>()
                        .setAuthenticator(new AuthenticationService())
                        .buildAuthFilter()
        ));
    }

}
