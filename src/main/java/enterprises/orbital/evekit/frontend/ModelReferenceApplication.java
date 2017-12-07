package enterprises.orbital.evekit.frontend;

import enterprises.orbital.base.OrbitalProperties;
import enterprises.orbital.base.PersistentProperty;
import enterprises.orbital.db.DBPropertyProvider;
import enterprises.orbital.evekit.ws.eve.ModelEveWS;
import enterprises.orbital.evekit.ws.map.ModelMapWS;
import enterprises.orbital.evekit.ws.server.ModelServerWS;

import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ModelReferenceApplication extends Application {
  // Property which holds the name of the persistence unit for properties
  public static final String PROP_PROPERTIES_PU = "enterprises.orbital.evekit.ref-model-frontend.properties.persistence_unit";
  public static final String PROP_APP_PATH      = "enterprises.orbital.evekit.ref-model-frontend.apppath";
  public static final String DEF_APP_PATH       = "http://localhost/ref-model";

  public ModelReferenceApplication() throws IOException {
    // Populate properties
    OrbitalProperties.addPropertyFile("EveKitRefModelFrontend.properties");
    // Sent persistence unit for properties
    PersistentProperty.setProvider(new DBPropertyProvider(OrbitalProperties.getGlobalProperty(PROP_PROPERTIES_PU)));
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new HashSet<Class<?>>();
    // Model API resources
    resources.add(ModelServerWS.class);
    resources.add(ModelEveWS.class);
    resources.add(ModelMapWS.class);
    // Swagger additions
    resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
    resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    // Return resource set
    return resources;
  }

}
