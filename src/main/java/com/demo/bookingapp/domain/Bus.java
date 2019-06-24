package com.demo.bookingapp.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bus.
 */
@Entity
@Table(name = "bus")
public class Bus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bus_name")
    private String busName;

    @Column(name = "bus_number")
    private String busNumber;

    @Column(name = "b_fare")
    private Integer bFare;

    @Column(name = "b_timing")
    private String bTiming;

    @OneToMany(mappedBy = "bus")
    private Set<Transport> buses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusName() {
        return busName;
    }

    public Bus busName(String busName) {
        this.busName = busName;
        return this;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public Bus busNumber(String busNumber) {
        this.busNumber = busNumber;
        return this;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public Integer getbFare() {
        return bFare;
    }

    public Bus bFare(Integer bFare) {
        this.bFare = bFare;
        return this;
    }

    public void setbFare(Integer bFare) {
        this.bFare = bFare;
    }

    public String getbTiming() {
        return bTiming;
    }

    public Bus bTiming(String bTiming) {
        this.bTiming = bTiming;
        return this;
    }

    public void setbTiming(String bTiming) {
        this.bTiming = bTiming;
    }

    public Set<Transport> getBuses() {
        return buses;
    }

    public Bus buses(Set<Transport> transports) {
        this.buses = transports;
        return this;
    }

    public Bus addBus(Transport transport) {
        this.buses.add(transport);
        transport.setBus(this);
        return this;
    }

    public Bus removeBus(Transport transport) {
        this.buses.remove(transport);
        transport.setBus(null);
        return this;
    }

    public void setBuses(Set<Transport> transports) {
        this.buses = transports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bus)) {
            return false;
        }
        return id != null && id.equals(((Bus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bus{" +
            "id=" + getId() +
            ", busName='" + getBusName() + "'" +
            ", busNumber='" + getBusNumber() + "'" +
            ", bFare=" + getbFare() +
            ", bTiming='" + getbTiming() + "'" +
            "}";
    }
}
