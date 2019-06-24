package com.demo.bookingapp.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Train.
 */
@Entity
@Table(name = "train")
public class Train implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_name")
    private String trainName;

    @Column(name = "train_number")
    private String trainNumber;

    @Column(name = "t_fare")
    private Integer tFare;

    @Column(name = "t_timing")
    private String tTiming;

    @OneToMany(mappedBy = "train")
    private Set<Transport> trains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public Train trainName(String trainName) {
        this.trainName = trainName;
        return this;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public Train trainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
        return this;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Integer gettFare() {
        return tFare;
    }

    public Train tFare(Integer tFare) {
        this.tFare = tFare;
        return this;
    }

    public void settFare(Integer tFare) {
        this.tFare = tFare;
    }

    public String gettTiming() {
        return tTiming;
    }

    public Train tTiming(String tTiming) {
        this.tTiming = tTiming;
        return this;
    }

    public void settTiming(String tTiming) {
        this.tTiming = tTiming;
    }

    public Set<Transport> getTrains() {
        return trains;
    }

    public Train trains(Set<Transport> transports) {
        this.trains = transports;
        return this;
    }

    public Train addTrain(Transport transport) {
        this.trains.add(transport);
        transport.setTrain(this);
        return this;
    }

    public Train removeTrain(Transport transport) {
        this.trains.remove(transport);
        transport.setTrain(null);
        return this;
    }

    public void setTrains(Set<Transport> transports) {
        this.trains = transports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Train)) {
            return false;
        }
        return id != null && id.equals(((Train) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Train{" +
            "id=" + getId() +
            ", trainName='" + getTrainName() + "'" +
            ", trainNumber='" + getTrainNumber() + "'" +
            ", tFare=" + gettFare() +
            ", tTiming='" + gettTiming() + "'" +
            "}";
    }
}
