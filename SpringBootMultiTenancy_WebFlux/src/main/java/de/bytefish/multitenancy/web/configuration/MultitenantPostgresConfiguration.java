// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.multitenancy.web.configuration;

import de.bytefish.multitenancy.routing.PostgresTenantConnectionFactory;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;

import static java.util.Map.entry;

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories
public class MultitenantPostgresConfiguration extends AbstractR2dbcConfiguration {

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        var connectionFactory = postgresConnectionFactory();

        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }

    private AbstractRoutingConnectionFactory postgresConnectionFactory() {
        var routingConnectionFactory = new PostgresTenantConnectionFactory();

        routingConnectionFactory.setLenientFallback(false);
        routingConnectionFactory.setTargetConnectionFactories(tenants());

        return routingConnectionFactory;

    }

    private Map<String, ConnectionFactory> tenants() {
        return Map.ofEntries(
                entry("TenantOne", tenantOne()),
                entry("TenantTwo", tenantTwo())
        );
    }

    private ConnectionFactory tenantOne() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("sampledb")
                .username("philipp")
                .password("test_pwd").build());
    }

    private ConnectionFactory tenantTwo() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("sampledb2")
                .username("philipp")
                .password("test_pwd").build());
    }
}
