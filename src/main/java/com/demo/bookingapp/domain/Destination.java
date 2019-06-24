package com.demo.bookingapp.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Destination.
 */
@Entity
@Table(name = "destination")
public class Destination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "destination_name")
    private String destinationName;

    @OneToMany(mappedBy = "destination")
    private Set<Transport> destinations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public Destination destinationName(String destinationName) {
        this.destinationName = destinationName;
        return this;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Set<Transport> getDestinations() {
        return destinations;
    }

    public Destination destinations(Set<Transport> transports) {
        this.destinations = transports;
        return this;
    }

    public Destination addDestination(Transport transport) {
        this.destinations.add(transport);
        transport.setDestination(this);
        return this;
    }

    public Destination removeDestination(Transport transport) {
        this.destinations.remove(transport);
        transport.setDestination(null);
        return this;
    }

    public void setDestinations(Set<Transport> transports) {
        this.destinations = transports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Destination)) {
            return false;
        }
        return id != null && id.equals(((Destination) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Destination{" +
            "id=" + getId() +
            ", destinationName='" + getDestinationName() + "'" +
            "}";
    }
}
