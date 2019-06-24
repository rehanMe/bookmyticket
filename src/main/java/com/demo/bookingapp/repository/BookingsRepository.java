package com.demo.bookingapp.repository;

import com.demo.bookingapp.domain.Bookings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bookings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long> {

}
