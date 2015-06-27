package com.autentia.tutorial.idempotency.order.service;

/**
 *
 * @author marlandy
 */
public interface OrderKeyGenerator {

    String generateKey();

    boolean isValidKey(String key);

}
