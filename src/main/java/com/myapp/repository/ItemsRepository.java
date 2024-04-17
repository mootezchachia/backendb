package com.myapp.repository;

import com.myapp.domain.Items;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Items entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {}
