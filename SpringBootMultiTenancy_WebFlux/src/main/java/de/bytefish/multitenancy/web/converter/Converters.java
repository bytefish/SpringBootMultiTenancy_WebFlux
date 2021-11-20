// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.multitenancy.web.converter;

import de.bytefish.multitenancy.model.Customer;
import de.bytefish.multitenancy.web.model.CustomerDto;

public class Converters {

    private Converters() {

    }

    public static CustomerDto convert(Customer source) {
        if(source == null) {
            return null;
        }

        return new CustomerDto(source.getId(), source.getFirstName(), source.getLastName());
    }

    public static Customer convert(CustomerDto source) {
        if(source == null) {
            return null;
        }

        return new Customer(source.getId(), source.getFirstName(), source.getLastName());
    }
}
