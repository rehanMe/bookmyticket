package com.demo.bookingapp.web.rest;

import com.demo.bookingapp.domain.Bus;
import com.demo.bookingapp.repository.BusRepository;
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
 * REST controller for managing {@link com.demo.bookingapp.domain.Bus}.
 */
@RestController
@RequestMapping("/api")
public class BusResource {

    private final Logger log = LoggerFactory.getLogger(BusResource.class);

    private static final String ENTITY_NAME = "bus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusRepository busRepository;

    public BusResource(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    /**
     * {@code POST  /buses} : Create a new bus.
     *
     * @param bus the bus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bus, or with status {@code 400 (Bad Request)} if the bus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buses")
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus) throws URISyntaxException {
        log.debug("REST request to save Bus : {}", bus);
        if (bus.getId() != null) {
            throw new BadRequestAlertException("A new bus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bus result = busRepository.save(bus);
        return ResponseEntity.created(new URI("/api/buses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buses} : Updates an existing bus.
     *
     * @param bus the bus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bus,
     * or with status {@code 400 (Bad Request)} if the bus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buses")
    public ResponseEntity<Bus> updateBus(@RequestBody Bus bus) throws URISyntaxException {
        log.debug("REST request to update Bus : {}", bus);
        if (bus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bus result = busRepository.save(bus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /buses} : get all the buses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buses in body.
     */
    @GetMapping("/buses")
    public List<Bus> getAllBuses() {
        log.debug("REST request to get all Buses");
        return busRepository.findAll();
    }

    /**
     * {@code GET  /buses/:id} : get the "id" bus.
     *
     * @param id the id of the bus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buses/{id}")
    public ResponseEntity<Bus> getBus(@PathVariable Long id) {
        log.debug("REST request to get Bus : {}", id);
        Optional<Bus> bus = busRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bus);
    }

    /**
     * {@code DELETE  /buses/:id} : delete the "id" bus.
     *
     * @param id the id of the bus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buses/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        log.debug("REST request to delete Bus : {}", id);
        busRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
