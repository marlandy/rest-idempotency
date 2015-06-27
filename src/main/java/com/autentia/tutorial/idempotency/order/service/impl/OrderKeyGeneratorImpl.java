package com.autentia.tutorial.idempotency.order.service.impl;

import com.autentia.tutorial.idempotency.order.dao.OrderDao;
import com.autentia.tutorial.idempotency.order.service.OrderKeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * Generates a key for an order in that way:
 *
 * ID + "_" + HASH(ID + SECRET_KEY)
 *
 * e.g: 17000028_d1f769d0d6fae731ef1fb521e068ad71
 *
 * @author marlandy
 */
@Service
public class OrderKeyGeneratorImpl implements OrderKeyGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(OrderKeyGeneratorImpl.class);

    private static final String SECRET = ".A,B$ppV&1^234#1R79o(¿4!,cRB$pp[]&1^<";

    private final OrderDao orderDao;

    @Autowired
    public OrderKeyGeneratorImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public String generateKey() {
        String orderId = Integer.toString(orderDao.createOrderId());
        String key = orderId + "_" + generateHash(orderId);
        LOG.info("Generated key {}", key);
        return key;
    }

    @Override
    public boolean isValidKey(String key) {
        if (key == null || !isValidFormat(key)) {
            LOG.info("Key format is invalid {}", key);
            return false;
        }
        String id = getIdFromKey(key);
        String generatedKey = id + "_" + generateHash(id);
        return generatedKey.equals(key);
    }

    private boolean isValidFormat(String key) {
        String[] parts = key.split("_");
        return parts.length == 2;
    }

    private String getIdFromKey(String key) {
        return key.split("_")[0];
    }

    private String generateHash(String id) {
        final String stringToHash = id + SECRET;
        return DigestUtils.md5DigestAsHex(stringToHash.getBytes());
    }

}
