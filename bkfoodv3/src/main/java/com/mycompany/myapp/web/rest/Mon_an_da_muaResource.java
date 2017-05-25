package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Mon_an_da_mua;

import com.mycompany.myapp.repository.Mon_an_da_muaRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mon_an_da_mua.
 */
@RestController
@RequestMapping("/api")
public class Mon_an_da_muaResource {

    private final Logger log = LoggerFactory.getLogger(Mon_an_da_muaResource.class);

    private static final String ENTITY_NAME = "mon_an_da_mua";
        
    private final Mon_an_da_muaRepository mon_an_da_muaRepository;

    public Mon_an_da_muaResource(Mon_an_da_muaRepository mon_an_da_muaRepository) {
        this.mon_an_da_muaRepository = mon_an_da_muaRepository;
    }

    /**
     * POST  /mon-an-da-muas : Create a new mon_an_da_mua.
     *
     * @param mon_an_da_mua the mon_an_da_mua to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mon_an_da_mua, or with status 400 (Bad Request) if the mon_an_da_mua has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mon-an-da-muas")
    @Timed
    public ResponseEntity<Mon_an_da_mua> createMon_an_da_mua(@Valid @RequestBody Mon_an_da_mua mon_an_da_mua) throws URISyntaxException {
        log.debug("REST request to save Mon_an_da_mua : {}", mon_an_da_mua);
        if (mon_an_da_mua.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mon_an_da_mua cannot already have an ID")).body(null);
        }
        Mon_an_da_mua result = mon_an_da_muaRepository.save(mon_an_da_mua);
        return ResponseEntity.created(new URI("/api/mon-an-da-muas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mon-an-da-muas : Updates an existing mon_an_da_mua.
     *
     * @param mon_an_da_mua the mon_an_da_mua to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mon_an_da_mua,
     * or with status 400 (Bad Request) if the mon_an_da_mua is not valid,
     * or with status 500 (Internal Server Error) if the mon_an_da_mua couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mon-an-da-muas")
    @Timed
    public ResponseEntity<Mon_an_da_mua> updateMon_an_da_mua(@Valid @RequestBody Mon_an_da_mua mon_an_da_mua) throws URISyntaxException {
        log.debug("REST request to update Mon_an_da_mua : {}", mon_an_da_mua);
        if (mon_an_da_mua.getId() == null) {
            return createMon_an_da_mua(mon_an_da_mua);
        }
        Mon_an_da_mua result = mon_an_da_muaRepository.save(mon_an_da_mua);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mon_an_da_mua.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mon-an-da-muas : get all the mon_an_da_muas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mon_an_da_muas in body
     */
    @GetMapping("/mon-an-da-muas")
    @Timed
    public List<Mon_an_da_mua> getAllMon_an_da_muas() {
        log.debug("REST request to get all Mon_an_da_muas");
        List<Mon_an_da_mua> mon_an_da_muas = mon_an_da_muaRepository.findAll();
        return mon_an_da_muas;
    }

    /**
     * GET  /mon-an-da-muas/:id : get the "id" mon_an_da_mua.
     *
     * @param id the id of the mon_an_da_mua to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mon_an_da_mua, or with status 404 (Not Found)
     */
    @GetMapping("/mon-an-da-muas/{id}")
    @Timed
    public ResponseEntity<Mon_an_da_mua> getMon_an_da_mua(@PathVariable Long id) {
        log.debug("REST request to get Mon_an_da_mua : {}", id);
        Mon_an_da_mua mon_an_da_mua = mon_an_da_muaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mon_an_da_mua));
    }

    /**
     * DELETE  /mon-an-da-muas/:id : delete the "id" mon_an_da_mua.
     *
     * @param id the id of the mon_an_da_mua to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mon-an-da-muas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMon_an_da_mua(@PathVariable Long id) {
        log.debug("REST request to delete Mon_an_da_mua : {}", id);
        mon_an_da_muaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
