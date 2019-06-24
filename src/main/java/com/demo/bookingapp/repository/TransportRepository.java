package com.demo.bookingapp.repository;

import com.demo.bookingapp.domain.Transport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data  repository for the Transport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {
 @Query("SELECT h FROM Transport h where h.source.id = :source and h.destination.id = :destination ")
 List<Transport>  findBySourceAndDestination(@Param("source") Long source, @Param("destination") Long destination);}
