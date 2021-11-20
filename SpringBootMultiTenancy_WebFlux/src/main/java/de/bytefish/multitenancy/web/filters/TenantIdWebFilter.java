// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.multitenancy.web.filters;

import de.bytefish.multitenancy.constants.ApplicationConstants;
import de.bytefish.multitenancy.web.constants.HeaderNames;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TenantIdWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {

        var headerValues = serverWebExchange.getRequest().getHeaders().get(HeaderNames.TenantId);

        if(headerValues == null || headerValues.size() == 0) {
            return webFilterChain.filter(serverWebExchange);
        }

        // Make a guess. Just get the first Key, if we have multiple Tenant Headers:
        String tenantKey = headerValues.get(0);

        return webFilterChain
                .filter(serverWebExchange)
                .contextWrite(ctx -> ctx.put(ApplicationConstants.TenantKey, tenantKey));
    }
}