package com.demo.bookingapp.web.rest;

import com.demo.bookingapp.domain.Transport;
import com.demo.bookingapp.repository.TransportRepository;
import com.demo.bookingapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.demo.bookingapp.domain.Transport}.
 */
@RestController
@RequestMapping("/api")
public class TransportResource {

    private final Logger log = LoggerFactory.getLogger(TransportResource.class);

    private static final String ENTITY_NAME = "transport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransportRepository transportRepository;

    public TransportResource(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    /**
     * {@code POST  /transports} : Create a new transport.
     *
     * @param transport the transport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transport, or with status {@code 400 (Bad Request)} if the transport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transports")
    public ResponseEntity<Transport> createTransport(@Valid @RequestBody Transport transport) throws URISyntaxException {
        log.debug("REST request to save Transport : {}", transport);
        if (transport.getId() != null) {
            throw new BadRequestAlertException("A new transport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transport result = transportRepository.save(transport);
        return ResponseEntity.created(new URI("/api/transports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transports} : Updates an existing transport.
     *
     * @param transport the transport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transport,
     * or with status {@code 400 (Bad Request)} if the transport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transports")
    public ResponseEntity<Transport> updateTransport(@Valid @RequestBody Transport transport) throws URISyntaxException {
        log.debug("REST request to update Transport : {}", transport);
        if (transport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transport result = transportRepository.save(transport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transports} : get all the transports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transports in body.
     */
    @GetMapping("/transports")
    public List<Transport> getAllTransports() {
        log.debug("REST request to get all Transports");
        return transportRepository.findAll();
    }

    /**
     * {@code GET  /transports} : get all the transports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transports in body.
     */
    @GetMapping("/transports/search/{source}/{destination}")
    public List<Transport> searchAllTransports(@PathVariable Long source,@PathVariable Long destination) {
        log.debug("REST request to get all Transports");
        return transportRepository.findBySourceAndDestination(source, destination);
    }

    /**
     * {@code GET  /transports/:id} : get the "id" transport.
     *
     * @param id the id of the transport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transports/{id}")
    public ResponseEntity<Transport> getTransport(@PathVariable Long id) {
        log.debug("REST request to get Transport : {}", id);
        Optional<Transport> transport = transportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transport);
    }

    /**
     * {@code DELETE  /transports/:id} : delete the "id" transport.
     *
     * @param id the id of the transport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transports/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        log.debug("REST request to delete Transport : {}", id);
        transportRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
