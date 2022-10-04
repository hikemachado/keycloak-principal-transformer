/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2019 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.priax.wildfly;

import java.security.Principal;
import java.util.Locale;

import org.keycloak.KeycloakPrincipal;
import org.wildfly.extension.elytron.capabilities.PrincipalTransformer;
import org.wildfly.security.auth.principal.NamePrincipal;
import org.wildfly.security.auth.server.NameRewriter;

/**
 * A simple principal transformer which converts a principal name to all upper case characters.
 */
public class KeycloakPrincipalTransformer implements PrincipalTransformer {

    //private static final String JBOSS_LOCAL_USER = "$local";
    //private static final PrincipalTransformer delegate = PrincipalTransformer.from(new CaseRewriter().asPrincipalRewriter());

    public Principal apply(Principal original) {

        return PrincipalTransformer.from(principal -> {
            if (principal == null) return null;
            KeycloakPrincipal orig = (KeycloakPrincipal) principal;
            return new NamePrincipal(orig.getKeycloakSecurityContext().getIdToken().getPreferredUsername());
            
        }).apply(original);

        //KeycloakPrincipal orig = (KeycloakPrincipal) original;
        //NamePrincipal n = new NamePrincipal(orig.getKeycloakSecurityContext().getIdToken().getPreferredUsername());
        //return n;
    }

    /*private static class CaseRewriter implements NameRewriter {
        public String rewriteName(String original) {
            if (original == null || original.equals(JBOSS_LOCAL_USER)) return original;
            return original.toUpperCase(Locale.ROOT);
        }
    }*/

}

