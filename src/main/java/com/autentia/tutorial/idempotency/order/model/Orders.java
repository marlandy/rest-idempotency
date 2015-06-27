package com.autentia.tutorial.idempotency.order.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marlandy
 */
@XmlRootElement
public class Orders {

    private List<Order> orders;

    public Orders() {
        this.orders = new ArrayList<>();
    }

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    @XmlElement(name = "order")
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
