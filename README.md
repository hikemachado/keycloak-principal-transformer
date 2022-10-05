Implementing and making use of a custom-principal-transformer
=============================================================

This Transformer is to be used in aggregated-realm when authencation will happen in KeyCloak and authorization in another real like jdbc-realm to change to the expected type NamePrincipal as keycloak retun a KeycloakPrincipal.

Usage
*****

Compile:

        mvn clean install

Add a module that contains our custom principal transformer to WildFly:

        bin/jboss-cli.sh
        module add --name=org.wildfly.security.examples.custom-principal-transformer --resources=/PATH_TO_ELYTRON_EXAMPLES/elytron-examples/custom-principal-transformer/target/custom-principal-transformer-1.0.0.Alpha1-SNAPSHOT.jar --dependencies=org.wildfly.security.elytron,org.wildfly.extension.elytron

Add a ```custom-principal-transformer``` in the Elytron subsystem that references this new module and our custom principal transformer class that is contained in this module:

        /subsystem=elytron/custom-principal-transformer=myPrincipalTransformer:add(module=org.wildfly.security.examples.custom-principal-transformer, class-name=org.wildfly.security.examples.CasePrincipalTransformer)

This results in the following XML in the Elytron subsystem:

        <custom-principal-transformer name="myPrincipalTransformer" module="org.wildfly.security.examples.custom-principal-transformer" class-name="org.wildfly.security.examples.CasePrincipalTransformer"/>

Now, make use of the ```custom-principal-transformer```:

        /subsystem=elytron/security-domain=ManagementDomain:write-attribute(name=pre-realm-principal-transformer,value=myPrincipalTransformer)


Installing offline mode
=======================

        $ EAP_HOME/bin/jboss-cli.sh

        embed-server --std-out=echo

Add a module that contains our custom principal transformer to WildFly:

        module add --name=io.priax.wildfly.custom-principal-transformer --resources=\PATH\TO\JAR\custom-principal-transformer-1.0.0-FINAL.jar --dependencies=org.wildfly.security.elytron,org.wildfly.extension.elytron,org.keycloak.keycloak-wildfly-elytron-oidc-adapter,org.keycloak.keycloak-undertow-adapter,org.keycloak.keycloak-adapter-spi,org.keycloak.keycloak-adapter-core,org.keycloak.keycloak-core,org.keycloak.keycloak-common

Add a ```custom-principal-transformer``` in the Elytron subsystem that references this new module and our custom principal transformer class that is contained in this module:

        /subsystem=elytron/custom-principal-transformer=myPrincipalTransformer:add(module=io.priax.wildfly.custom-principal-transformer, class-name=io.priax.wildfly.KeycloakPrincipalTransformer)

This results in the following XML in the Elytron subsystem:

        <custom-principal-transformer name="myPrincipalTransformer" module="io.priax.wildfly.custom-principal-transformer" class-name="io.priax.wildfly.KeycloakPrincipalTransformer"/>

Now, make use of the ```custom-principal-transformer```.


Module.xml example
*****
```
<?xml version='1.0' encoding='UTF-8'?>

<module xmlns="urn:jboss:module:1.1" name="io.priax.wildfly.custom-principal-transformer">

    <resources>
        <resource-root path="custom-principal-transformer-1.0.0-FINAL.jar"/>
    </resources>

    <dependencies>
        <module name="org.wildfly.security.elytron"/>
        <module name="org.wildfly.extension.elytron"/>
	  <module name="org.keycloak.keycloak-wildfly-elytron-oidc-adapter"/>
        <module name="org.keycloak.keycloak-undertow-adapter"/>
        <module name="org.keycloak.keycloak-adapter-spi"/>
        <module name="org.keycloak.keycloak-adapter-core"/>
        <module name="org.keycloak.keycloak-core"/>
        <module name="org.keycloak.keycloak-common"/>
    </dependencies>
</module>
```