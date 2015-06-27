package com.autentia.tutorial.idempotency.user.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author marlandy
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "age"})
public class User {

    private Integer id;

    private String name;

    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        final User other = (User) o;
        return new EqualsBuilder().append(this.id, other.getId()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).hashCode();
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", age=" + age + '}';
    }

}
