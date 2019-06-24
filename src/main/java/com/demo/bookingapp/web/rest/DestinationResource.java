package com.demo.bookingapp.web.rest;

import com.demo.bookingapp.domain.Destination;
import com.demo.bookingapp.repository.DestinationRepository;
import com.demo.bookingapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.demo.bookingapp.domain.Destination}.
 */
@RestController
@RequestMapping("/api")
public class DestinationResource {

    private final Logger log = LoggerFactory.getLogger(DestinationResource.class);

    private static final String ENTITY_NAME = "destination";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DestinationRepository destinationRepository;

    public DestinationResource(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    /**
     * {@code POST  /destinations} : Create a new destination.
     *
     * @param destination the destination to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new destination, or with status {@code 400 (Bad Request)} if the destination has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/destinations")
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) throws URISyntaxException {
        log.debug("REST request to save Destination : {}", destination);
        if (destination.getId() != null) {
            throw new BadRequestAlertException("A new destination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Destination result = destinationRepository.save(destination);
        return ResponseEntity.created(new URI("/api/destinations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /destinations} : Updates an existing destination.
     *
     * @param destination the destination to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated destination,
     * or with status {@code 400 (Bad Request)} if the destination is not valid,
     * or with status {@code 500 (Internal Server Error)} if the destination couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/destinations")
    public ResponseEntity<Destination> updateDestination(@RequestBody Destination destination) throws URISyntaxException {
        log.debug("REST request to update Destination : {}", destination);
        if (destination.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Destination result = destinationRepository.save(destination);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, destination.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /destinations} : get all the destinations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of destinations in body.
     */
    @GetMapping("/destinations")
    public List<Destination> getAllDestinations() {
        log.debug("REST request to get all Destinations");
        return destinationRepository.findAll();
    }

    /**
     * {@code GET  /destinations/:id} : get the "id" destination.
     *
     * @param id the id of the destination to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the destination, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/destinations/{id}")
    public ResponseEntity<Destination> getDestination(@PathVariable Long id) {
        log.debug("REST request to get Destination : {}", id);
        Optional<Destination> destination = destinationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(destination);
    }

    /**
     * {@code DELETE  /destinations/:id} : delete the "id" destination.
     *
     * @param id the id of the destination to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/destinations/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        log.debug("REST request to delete Destination : {}", id);
        destinationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
