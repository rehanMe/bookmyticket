package com.demo.bookingapp.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Source.
 */
@Entity
@Table(name = "source")
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_name")
    private String sourceName;

    @OneToMany(mappedBy = "source")
    private Set<Transport> sources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public Source sourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Set<Transport> getSources() {
        return sources;
    }

    public Source sources(Set<Transport> transports) {
        this.sources = transports;
        return this;
    }

    public Source addSource(Transport transport) {
        this.sources.add(transport);
        transport.setSource(this);
        return this;
    }

    public Source removeSource(Transport transport) {
        this.sources.remove(transport);
        transport.setSource(null);
        return this;
    }

    public void setSources(Set<Transport> transports) {
        this.sources = transports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Source)) {
            return false;
        }
        return id != null && id.equals(((Source) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Source{" +
            "id=" + getId() +
            ", sourceName='" + getSourceName() + "'" +
            "}";
    }
}
