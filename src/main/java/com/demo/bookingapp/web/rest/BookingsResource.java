package com.demo.bookingapp.web.rest;

import com.demo.bookingapp.domain.Bookings;
import com.demo.bookingapp.repository.BookingsRepository;
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
 * REST controller for managing {@link com.demo.bookingapp.domain.Bookings}.
 */
@RestController
@RequestMapping("/api")
public class BookingsResource {

    private final Logger log = LoggerFactory.getLogger(BookingsResource.class);

    private static final String ENTITY_NAME = "bookings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookingsRepository bookingsRepository;

    public BookingsResource(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    /**
     * {@code POST  /bookings} : Create a new bookings.
     *
     * @param bookings the bookings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookings, or with status {@code 400 (Bad Request)} if the bookings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bookings")
    public ResponseEntity<Bookings> createBookings(@Valid @RequestBody Bookings bookings) throws URISyntaxException {
        log.debug("REST request to save Bookings : {}", bookings);
        if (bookings.getId() != null) {
            throw new BadRequestAlertException("A new bookings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bookings result = bookingsRepository.save(bookings);
        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bookings} : Updates an existing bookings.
     *
     * @param bookings the bookings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookings,
     * or with status {@code 400 (Bad Request)} if the bookings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bookings")
    public ResponseEntity<Bookings> updateBookings(@Valid @RequestBody Bookings bookings) throws URISyntaxException {
        log.debug("REST request to update Bookings : {}", bookings);
        if (bookings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bookings result = bookingsRepository.save(bookings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bookings.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bookings} : get all the bookings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookings in body.
     */
    @GetMapping("/bookings")
    public List<Bookings> getAllBookings() {
        log.debug("REST request to get all Bookings");
        return bookingsRepository.findAll();
    }

    /**
     * {@code GET  /bookings/:id} : get the "id" bookings.
     *
     * @param id the id of the bookings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Bookings> getBookings(@PathVariable Long id) {
        log.debug("REST request to get Bookings : {}", id);
        Optional<Bookings> bookings = bookingsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bookings);
    }

    /**
     * {@code DELETE  /bookings/:id} : delete the "id" bookings.
     *
     * @param id the id of the bookings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> deleteBookings(@PathVariable Long id) {
        log.debug("REST request to delete Bookings : {}", id);
        bookingsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
