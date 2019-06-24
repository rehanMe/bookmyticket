package com.demo.bookingapp.repository;

import com.demo.bookingapp.domain.Train;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Train entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

}
