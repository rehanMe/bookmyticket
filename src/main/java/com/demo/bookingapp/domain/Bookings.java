package com.demo.bookingapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.demo.bookingapp.domain.enumeration.Status;

/**
 * A Bookings.
 */
@Entity
@Table(name = "bookings")
public class Bookings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 16)
    @Column(name = "card_no", length = 16, nullable = false)
    private String cardNo;

    @NotNull
    @Size(max = 5)
    @Column(name = "valid_thru", length = 5, nullable = false)
    private String validThru;

    @NotNull
    @Column(name = "cvv", nullable = false)
    private Integer cvv;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    private Transport transport;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public Bookings cardNo(String cardNo) {
        this.cardNo = cardNo;
        return this;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getValidThru() {
        return validThru;
    }

    public Bookings validThru(String validThru) {
        this.validThru = validThru;
        return this;
    }

    public void setValidThru(String validThru) {
        this.validThru = validThru;
    }

    public Integer getCvv() {
        return cvv;
    }

    public Bookings cvv(Integer cvv) {
        this.cvv = cvv;
        return this;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getName() {
        return name;
    }

    public Bookings name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public Bookings status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Transport getTransport() {
        return transport;
    }

    public Bookings transport(Transport transport) {
        this.transport = transport;
        return this;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Bookings customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bookings)) {
            return false;
        }
        return id != null && id.equals(((Bookings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bookings{" +
            "id=" + getId() +
            ", cardNo='" + getCardNo() + "'" +
            ", validThru='" + getValidThru() + "'" +
            ", cvv=" + getCvv() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
