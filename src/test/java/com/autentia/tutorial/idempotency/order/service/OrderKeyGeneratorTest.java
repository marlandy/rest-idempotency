package com.autentia.tutorial.idempotency.order.service;

import com.autentia.tutorial.idempotency.order.dao.OrderDao;
import com.autentia.tutorial.idempotency.order.service.impl.OrderKeyGeneratorImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author marlandy
 */
public class OrderKeyGeneratorTest {
    
    private static final int GENERATED_ID = 17000028;
    
    private OrderKeyGenerator generator;
    
    @Before
    public void init() {
        final OrderDao mockedDao = mock(OrderDao.class);
        when(mockedDao.createOrderId()).thenReturn(GENERATED_ID);
        generator = new OrderKeyGeneratorImpl(mockedDao);
    }
    
    @Test
    public void shouldGenerateAnOrderKeyFromId() {
        String key = generator.generateKey();
        assertNotNull(key);
    }
    
    @Test
    public void shouldCheckIfGeneratedKeyIsValid() {
        String key = generator.generateKey();
        assertTrue(generator.isValidKey(key));
    }
    
    @Test
    public void shouldFailIfGeneratedKeyIsModified() {
        String key = generator.generateKey();
        int firstChar = key.substring(0, 1).getBytes()[0];
        String modifiedKey = (char) (firstChar + 1) + key.substring(1);
        assertFalse(generator.isValidKey(modifiedKey));
    }
    
    @Test
    public void shouldFailIfKeyIsInvalid() {
        String invalidKey = "999992_bd84fe278c6152c821a80ee6038effe6";
        assertFalse(generator.isValidKey(invalidKey));
    }
    
}
