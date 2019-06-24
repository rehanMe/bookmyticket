package com.demo.bookingapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.demo.bookingapp.domain.enumeration.TransportType;

import com.demo.bookingapp.domain.enumeration.WeekDays;

/**
 * A Transport.
 */
@Entity
@Table(name = "transport")
public class Transport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false)
    private TransportType transportType;

    @NotNull
    @Column(name = "service_provider_name", nullable = false)
    private String serviceProviderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private WeekDays availability;

    @OneToMany(mappedBy = "transport")
    private Set<Bookings> transports = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("transports")
    private Train train;

    @ManyToOne
    @JsonIgnoreProperties("transports")
    private Bus bus;

    @ManyToOne
    @JsonIgnoreProperties("transports")
    private Airplane airplane;

    @ManyToOne
    @JsonIgnoreProperties("transports")
    private Source source;

    @ManyToOne
    @JsonIgnoreProperties("transports")
    private Destination destination;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public Transport transportType(TransportType transportType) {
        this.transportType = transportType;
        return this;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public Transport serviceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
        return this;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public WeekDays getAvailability() {
        return availability;
    }

    public Transport availability(WeekDays availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(WeekDays availability) {
        this.availability = availability;
    }

    public Set<Bookings> getTransports() {
        return transports;
    }

    public Transport transports(Set<Bookings> bookings) {
        this.transports = bookings;
        return this;
    }

    public Transport addTransport(Bookings bookings) {
        this.transports.add(bookings);
        bookings.setTransport(this);
        return this;
    }

    public Transport removeTransport(Bookings bookings) {
        this.transports.remove(bookings);
        bookings.setTransport(null);
        return this;
    }

    public void setTransports(Set<Bookings> bookings) {
        this.transports = bookings;
    }

    public Train getTrain() {
        return train;
    }

    public Transport train(Train train) {
        this.train = train;
        return this;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Bus getBus() {
        return bus;
    }

    public Transport bus(Bus bus) {
        this.bus = bus;
        return this;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public Transport airplane(Airplane airplane) {
        this.airplane = airplane;
        return this;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Source getSource() {
        return source;
    }

    public Transport source(Source source) {
        this.source = source;
        return this;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Destination getDestination() {
        return destination;
    }

    public Transport destination(Destination destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transport)) {
            return false;
        }
        return id != null && id.equals(((Transport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transport{" +
            "id=" + getId() +
            ", transportType='" + getTransportType() + "'" +
            ", serviceProviderName='" + getServiceProviderName() + "'" +
            ", availability='" + getAvailability() + "'" +
            "}";
    }
}
