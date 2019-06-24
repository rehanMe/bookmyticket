package com.demo.bookingapp.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Airplane.
 */
@Entity
@Table(name = "airplane")
public class Airplane implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_name")
    private String flightName;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "f_fare")
    private Integer fFare;

    @Column(name = "f_timing")
    private String fTiming;

    @OneToMany(mappedBy = "airplane")
    private Set<Transport> airplanes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightName() {
        return flightName;
    }

    public Airplane flightName(String flightName) {
        this.flightName = flightName;
        return this;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Airplane flightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getfFare() {
        return fFare;
    }

    public Airplane fFare(Integer fFare) {
        this.fFare = fFare;
        return this;
    }

    public void setfFare(Integer fFare) {
        this.fFare = fFare;
    }

    public String getfTiming() {
        return fTiming;
    }

    public Airplane fTiming(String fTiming) {
        this.fTiming = fTiming;
        return this;
    }

    public void setfTiming(String fTiming) {
        this.fTiming = fTiming;
    }

    public Set<Transport> getAirplanes() {
        return airplanes;
    }

    public Airplane airplanes(Set<Transport> transports) {
        this.airplanes = transports;
        return this;
    }

    public Airplane addAirplane(Transport transport) {
        this.airplanes.add(transport);
        transport.setAirplane(this);
        return this;
    }

    public Airplane removeAirplane(Transport transport) {
        this.airplanes.remove(transport);
        transport.setAirplane(null);
        return this;
    }

    public void setAirplanes(Set<Transport> transports) {
        this.airplanes = transports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Airplane)) {
            return false;
        }
        return id != null && id.equals(((Airplane) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Airplane{" +
            "id=" + getId() +
            ", flightName='" + getFlightName() + "'" +
            ", flightNumber='" + getFlightNumber() + "'" +
            ", fFare=" + getfFare() +
            ", fTiming='" + getfTiming() + "'" +
            "}";
    }
}
