package com.autentia.tutorial.idempotency.util;

import com.autentia.tutorial.idempotency.common.exception.InvalidDataException;
import com.autentia.tutorial.idempotency.order.model.Order;

/**
 *
 * @author marlandy
 */
public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void validateRequired(Object data, String field) {
        if (data == null) {
            throw new InvalidDataException(field + " is required");
        }
    }

    public static void validateOrderStatus(Integer status) {
        if (!(Order.CREATED.equals(status)
                || Order.FINISHED.equals(status)
                || Order.CANCELED.equals(status))) {
            throw new InvalidDataException("Order status must be: " + Order.CREATED + " (CREATED), "
                    + Order.FINISHED + " (FINISHED) or " + Order.CANCELED + " (CANCELED)");
        }
    }

}
