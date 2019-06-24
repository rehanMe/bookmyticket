package com.demo.bookingapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.demo.bookingapp.domain.enumeration.Gender;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_name")
    private String cName;

    @Column(name = "c_age")
    private Integer cAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_gender")
    private Gender cGender;

    @NotNull
    @Size(max = 10)
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "customer")
    private Set<Bookings> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public Customer cName(String cName) {
        this.cName = cName;
        return this;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getcAge() {
        return cAge;
    }

    public Customer cAge(Integer cAge) {
        this.cAge = cAge;
        return this;
    }

    public void setcAge(Integer cAge) {
        this.cAge = cAge;
    }

    public Gender getcGender() {
        return cGender;
    }

    public Customer cGender(Gender cGender) {
        this.cGender = cGender;
        return this;
    }

    public void setcGender(Gender cGender) {
        this.cGender = cGender;
    }

    public String getPhone() {
        return phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Bookings> getCustomers() {
        return customers;
    }

    public Customer customers(Set<Bookings> bookings) {
        this.customers = bookings;
        return this;
    }

    public Customer addCustomer(Bookings bookings) {
        this.customers.add(bookings);
        bookings.setCustomer(this);
        return this;
    }

    public Customer removeCustomer(Bookings bookings) {
        this.customers.remove(bookings);
        bookings.setCustomer(null);
        return this;
    }

    public void setCustomers(Set<Bookings> bookings) {
        this.customers = bookings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", cName='" + getcName() + "'" +
            ", cAge=" + getcAge() +
            ", cGender='" + getcGender() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
