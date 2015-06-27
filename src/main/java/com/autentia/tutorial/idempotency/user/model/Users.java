package com.autentia.tutorial.idempotency.user.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marlandy
 */
@XmlRootElement
public class Users {

    private List<User> users;

    public Users() {
    }

    public Users(List<User> users) {
        this.users = users;
    }

    @XmlElement(name = "user")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
