# EveKit Reference Data Model Frontend

This module provides a servlet which exposes EveKit reference model data through a REST API.  We use [Swagger](http://swagger.io) to annotate our REST API, which in turn makes it trivial to generate documentation and experiment with the API, as well as generate client libraries in various languages.  A public instance of this module runs [here](https://evekit-ref-model.orbital.enterprises).  However, the servlet for this module only exposes the REST API and a Swagger configuration file for driving supporting tools.  You can always view the live Swagger configuration file [here](https://evekit-ref-model.orbital.enterprises/api/swagger.json).

This module assumes you have already set up an appropriate database with EveKit account and model data.  See the [EveKit Frontend](https://github.com/OrbitalEnterprises/evekit-frontend) page for an overview of the EveKit service and instructions for setting up a backing database.

The rest of this guide describes how to configure, build and deploy the EveKit Reference Data Model Frontend.

## Configuration

The reference data model frontend requires the setting and substitution of several parameters which control database and servlet settings.  Since the model frontend is normally built with [Maven](http://maven.apache.org), configuration is handled by setting or overriding properties in your local Maven settings.xml file.  The following configuration parameters should be set:

| Parameter | Meaning |
|-----------|---------|
|enterprises.orbital.evekit.ref-model-frontend.basepath|The base location where the servlet is hosted, e.g. http://localhost:8080|
|enterprises.orbital.evekit.ref-model-frontend.appname|Name of the servlet when deployed|
|enterprises.orbital.evekit.ref-model-frontend.db.properties.url|Hibernate JDBC connection URL for properties|
|enterprises.orbital.evekit.ref-model-frontend.db.properties.user|Hibernate JDBC connection user name for properties|
|enterprises.orbital.evekit.ref-model-frontend.db.properties.password|Hibernate JDBC connection password for properties|
|enterprises.orbital.evekit.ref-model-frontend.db.properties.driver|Hibernate JDBC driver class name for properties|
|enterprises.orbital.evekit.ref-model-frontend.db.properties.dialect|Hibernate dialect class name for properties|

At build and deploy time, the parameters above are substituted into the following files:

* src/main/resources/META-INF/persistence.xml
* src/main/resources/EveKitRefModelFrontend.properties
* src/main/webapp/WEB-INF/web.xml

If you are not using Maven to build, you'll need to substitute these settings manually.

## Build

We use [Maven](http://maven.apache.org) to build all EveKit modules.  EveKit dependencies are released and published to [Maven Central](http://search.maven.org/).  EveKit front ends are not released and must be installed by cloning a repository.  To build the EveKit Reference Data Model Frontend, clone this repository and use "mvn install".  Make sure you have set all required configuration parameters before building (as described in the previous section).

## Deployment

This project is designed to easily deploy in a standard Servlet container.  Two parameters need to be substituted in the web.xml file in order for deployment to work correctly:

| Parameter | Meaning |
|-----------|---------|
|enterprises.orbital.evekit.ref-model-frontend.basepath|The base location where the servlet is hosted, e.g. http://localhost:8080|
|enterprises.orbital.evekit.ref-model-frontend.appname|Name of the servlet when deployed|

If you follow the configuration and build instructions above, these parameters will be substituted for you.  These settings are used to define the base path for the REST API endpoints (via Swagger).

### Deploying to Tomcat

The default pom.xml in the project includes the [Tomcat Maven plugin](http://tomcat.apache.org/maven-plugin.html) which makes it easy to deploy directly to a Tomcat instance.  This is normally done by adding two stanzas to your settings.xml:

```xml
<servers>
  <server>
    <id>LocalTomcatServer</id>
    <username>admin</username>
    <password>password</password>
  </server>    
</servers>

<profiles>
  <profile>
    <id>LocalTomcat</id>
    <properties>
      <enterprises.orbital.evekit.ref-model-frontend.tomcat.url>http://127.0.0.1:8080/manager/text</enterprises.orbital.evekit.ref-model-frontend.tomcat.url>
      <enterprises.orbital.evekit.ref-model-frontend.tomcat.server>LocalTomcatServer</enterprises.orbital.evekit.ref-model-frontend.tomcat.server>
      <enterprises.orbital.evekit.ref-model-frontend.tomcat.path>/evekit-ref-model</enterprises.orbital.evekit.ref-model-frontend.tomcat.path>
    </properties>	
  </profile>
</profiles>
```

The first stanza specifies the management credentials for your Tomcat instance.  The second stanza defines the properties needed to install into the server you just defined.  With these settings, you can deploy to your Tomcat instance as follows (this example uses Tomcat 7):

```
mvn -P LocalTomcat tomcat7:deploy
```

If you've already deployed, use "redploy" instead.  See the [Tomcat Maven plugin documentation](http://tomcat.apache.org/maven-plugin-2.2/) for more details on how the deployment plugin works.

## Usage

### Viewing Documentation and Trying the API with Swagger

You can always view the Swagger UI for the public instance of the model frontend from the public instance of [EveKit](https://evekit.orbital.enterprises), either by selecting API -> Model API, or by using this [direct link](https://evekit.orbital.enterprises//#/api/model/-1/-1/-1).

You can view the Swagger UI for another instance using the [Swagger UI online demo](http://petstore.swagger.io).  Navigate to the Swagger UI online demo page, then enter the URL for the Swagger configuration file for the instance you want to view.  Using the configuration properties above, the URL for the Swagger configuration file is always ${enterprises.orbital.evekit.model-frontend.basepath}/${enterprises.orbital.evekit.model-frontend.appname}/api/swagger.json .

### Model API Method Structure and Queries

Access to the reference data API follows the same conventions as the [EveKit Model API](https://github.com/OrbitalEnterprises/evekit-model-frontend) except that an access key is not required to access data.
Please refer to that repository for instructions on how to query the API.