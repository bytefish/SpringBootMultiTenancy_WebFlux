// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.multitenancy.utils;

import reactor.core.publisher.Mono;
import java.util.function.Supplier;

public class ReactorUtils {

    // https://github.com/reactor/reactor-core/issues/917
    public static <R> Mono<R> errorIfEmpty(Mono<R> mono, Supplier<Throwable> throwableSupplier) {
        return mono.switchIfEmpty(Mono.defer(() -> Mono.error(throwableSupplier.get())));
    }
}