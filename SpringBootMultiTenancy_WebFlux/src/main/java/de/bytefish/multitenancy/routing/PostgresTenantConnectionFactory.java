// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.multitenancy.routing;

import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import reactor.core.publisher.Mono;

public class PostgresTenantConnectionFactory extends AbstractRoutingConnectionFactory {

    public static final String TENANT_KEY = "tenant-key";

    static final class PostgresqlConnectionFactoryMetadata implements ConnectionFactoryMetadata {

        /**
         * The name of the PostgreSQL database product.
         */
        public static final String NAME = "PostgreSQL";

        static final PostgresqlConnectionFactoryMetadata INSTANCE = new PostgresqlConnectionFactoryMetadata();

        private PostgresqlConnectionFactoryMetadata() {
        }

        @Override
        public String getName() {
            return NAME;
        }
    }

    @Override
    protected Mono<Object> determineCurrentLookupKey() {
        return Mono
                .deferContextual(Mono::just)
                .filter(it -> it.hasKey(TENANT_KEY))
                .map(it -> it.get(TENANT_KEY));
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        return new PostgresqlConnectionFactoryMetadata();
    }
}
