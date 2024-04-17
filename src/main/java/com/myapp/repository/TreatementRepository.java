package com.myapp.repository;

import com.myapp.domain.Treatement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Treatement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatementRepository extends JpaRepository<Treatement, Integer> {}
