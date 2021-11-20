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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories
public class MultitenantPostgresConfig extends AbstractR2dbcConfiguration {

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {

        PostgresTenantConnectionFactory routingConnectionFactory = new PostgresTenantConnectionFactory();

        // Define the Tenants:
        Map<String, ConnectionFactory> connectionFactories = new HashMap<>();

        connectionFactories.put("TenantOne", tenantOne());
        connectionFactories.put("TenantTwo", tenantTwo());

        routingConnectionFactory.setTargetConnectionFactories(connectionFactories);
        routingConnectionFactory.setLenientFallback(false);

        routingConnectionFactory.afterPropertiesSet();

        return routingConnectionFactory;
    }

    public ConnectionFactory tenantOne() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("sampledb")
                .username("philipp")
                .password("test_pwd").build());
    }

    public ConnectionFactory tenantTwo() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("sampledb2")
                .username("philipp")
                .password("test_pwd").build());
    }
}
