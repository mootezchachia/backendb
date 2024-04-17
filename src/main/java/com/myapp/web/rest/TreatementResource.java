package com.myapp.web.rest;

import com.myapp.domain.Treatement;
import com.myapp.repository.TreatementRepository;
import com.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.myapp.domain.Treatement}.
 */
@RestController
@RequestMapping("/api/treatements")
@Transactional
public class TreatementResource {

    private final Logger log = LoggerFactory.getLogger(TreatementResource.class);

    private static final String ENTITY_NAME = "treatement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TreatementRepository treatementRepository;

    public TreatementResource(TreatementRepository treatementRepository) {
        this.treatementRepository = treatementRepository;
    }

    /**
     * {@code POST  /treatements} : Create a new treatement.
     *
     * @param treatement the treatement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new treatement, or with status {@code 400 (Bad Request)} if the treatement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Treatement> createTreatement(@RequestBody Treatement treatement) throws URISyntaxException {
        log.debug("REST request to save Treatement : {}", treatement);
        if (treatement.getId() != null) {
            throw new BadRequestAlertException("A new treatement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Treatement result = treatementRepository.save(treatement);
        return ResponseEntity
            .created(new URI("/api/treatements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /treatements/:id} : Updates an existing treatement.
     *
     * @param id the id of the treatement to save.
     * @param treatement the treatement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated treatement,
     * or with status {@code 400 (Bad Request)} if the treatement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the treatement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Treatement> updateTreatement(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody Treatement treatement
    ) throws URISyntaxException {
        log.debug("REST request to update Treatement : {}, {}", id, treatement);
        if (treatement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, treatement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!treatementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Treatement result = treatementRepository.save(treatement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, treatement.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /treatements/:id} : Partial updates given fields of an existing treatement, field will ignore if it is null
     *
     * @param id the id of the treatement to save.
     * @param treatement the treatement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated treatement,
     * or with status {@code 400 (Bad Request)} if the treatement is not valid,
     * or with status {@code 404 (Not Found)} if the treatement is not found,
     * or with status {@code 500 (Internal Server Error)} if the treatement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Treatement> partialUpdateTreatement(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody Treatement treatement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Treatement partially : {}, {}", id, treatement);
        if (treatement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, treatement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!treatementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Treatement> result = treatementRepository
            .findById(treatement.getId())
            .map(existingTreatement -> {
                if (treatement.getName() != null) {
                    existingTreatement.setName(treatement.getName());
                }
                if (treatement.getDescription() != null) {
                    existingTreatement.setDescription(treatement.getDescription());
                }

                return existingTreatement;
            })
            .map(treatementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, treatement.getId().toString())
        );
    }

    /**
     * {@code GET  /treatements} : get all the treatements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of treatements in body.
     */
    @GetMapping("")
    public List<Treatement> getAllTreatements() {
        log.debug("REST request to get all Treatements");
        return treatementRepository.findAll();
    }

    /**
     * {@code GET  /treatements/:id} : get the "id" treatement.
     *
     * @param id the id of the treatement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the treatement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Treatement> getTreatement(@PathVariable("id") Integer id) {
        log.debug("REST request to get Treatement : {}", id);
        Optional<Treatement> treatement = treatementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(treatement);
    }

    /**
     * {@code DELETE  /treatements/:id} : delete the "id" treatement.
     *
     * @param id the id of the treatement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatement(@PathVariable("id") Integer id) {
        log.debug("REST request to delete Treatement : {}", id);
        treatementRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
