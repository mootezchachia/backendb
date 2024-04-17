package com.myapp.web.rest;

import com.myapp.domain.Items;
import com.myapp.repository.ItemsRepository;
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
 * REST controller for managing {@link com.myapp.domain.Items}.
 */
@RestController
@RequestMapping("/api/items")
@Transactional
public class ItemsResource {

    private final Logger log = LoggerFactory.getLogger(ItemsResource.class);

    private static final String ENTITY_NAME = "items";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemsRepository itemsRepository;

    public ItemsResource(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    /**
     * {@code POST  /items} : Create a new items.
     *
     * @param items the items to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new items, or with status {@code 400 (Bad Request)} if the items has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Items> createItems(@RequestBody Items items) throws URISyntaxException {
        log.debug("REST request to save Items : {}", items);
        if (items.getId() != null) {
            throw new BadRequestAlertException("A new items cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Items result = itemsRepository.save(items);
        return ResponseEntity
            .created(new URI("/api/items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /items/:id} : Updates an existing items.
     *
     * @param id the id of the items to save.
     * @param items the items to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated items,
     * or with status {@code 400 (Bad Request)} if the items is not valid,
     * or with status {@code 500 (Internal Server Error)} if the items couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Items> updateItems(@PathVariable(value = "id", required = false) final Integer id, @RequestBody Items items)
        throws URISyntaxException {
        log.debug("REST request to update Items : {}, {}", id, items);
        if (items.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, items.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Items result = itemsRepository.save(items);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, items.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /items/:id} : Partial updates given fields of an existing items, field will ignore if it is null
     *
     * @param id the id of the items to save.
     * @param items the items to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated items,
     * or with status {@code 400 (Bad Request)} if the items is not valid,
     * or with status {@code 404 (Not Found)} if the items is not found,
     * or with status {@code 500 (Internal Server Error)} if the items couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Items> partialUpdateItems(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody Items items
    ) throws URISyntaxException {
        log.debug("REST request to partial update Items partially : {}, {}", id, items);
        if (items.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, items.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Items> result = itemsRepository
            .findById(items.getId())
            .map(existingItems -> {
                if (items.getName() != null) {
                    existingItems.setName(items.getName());
                }
                if (items.getDescription() != null) {
                    existingItems.setDescription(items.getDescription());
                }

                return existingItems;
            })
            .map(itemsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, items.getId().toString())
        );
    }

    /**
     * {@code GET  /items} : get all the items.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of items in body.
     */
    @GetMapping("")
    public List<Items> getAllItems() {
        log.debug("REST request to get all Items");
        return itemsRepository.findAll();
    }

    /**
     * {@code GET  /items/:id} : get the "id" items.
     *
     * @param id the id of the items to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the items, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Items> getItems(@PathVariable("id") Integer id) {
        log.debug("REST request to get Items : {}", id);
        Optional<Items> items = itemsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(items);
    }

    /**
     * {@code DELETE  /items/:id} : delete the "id" items.
     *
     * @param id the id of the items to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItems(@PathVariable("id") Integer id) {
        log.debug("REST request to delete Items : {}", id);
        itemsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
