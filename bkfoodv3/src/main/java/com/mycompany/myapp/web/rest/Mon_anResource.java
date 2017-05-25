package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Mon_an;

import com.mycompany.myapp.repository.Mon_anRepository;
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
import org.springframework.http.MediaType;
/**
 * REST controller for managing Mon_an.
 */
@RestController
@RequestMapping("/api")
public class Mon_anResource {

    private final Logger log = LoggerFactory.getLogger(Mon_anResource.class);

    private static final String ENTITY_NAME = "mon_an";

    private final Mon_anRepository mon_anRepository;

    public Mon_anResource(Mon_anRepository mon_anRepository) {
        this.mon_anRepository = mon_anRepository;
    }

    /**
     * POST  /mon-ans : Create a new mon_an.
     *
     * @param mon_an the mon_an to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mon_an, or with status 400 (Bad Request) if the mon_an has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mon-ans")
    @Timed
    public ResponseEntity<Mon_an> createMon_an(@Valid @RequestBody Mon_an mon_an) throws URISyntaxException {
        log.debug("REST request to save Mon_an : {}", mon_an);
        if (mon_an.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mon_an cannot already have an ID")).body(null);
        }
        Mon_an result = mon_anRepository.save(mon_an);
        return ResponseEntity.created(new URI("/api/mon-ans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mon-ans : Updates an existing mon_an.
     *
     * @param mon_an the mon_an to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mon_an,
     * or with status 400 (Bad Request) if the mon_an is not valid,
     * or with status 500 (Internal Server Error) if the mon_an couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mon-ans")
    @Timed
    public ResponseEntity<Mon_an> updateMon_an(@Valid @RequestBody Mon_an mon_an) throws URISyntaxException {
        log.debug("REST request to update Mon_an : {}", mon_an);
        if (mon_an.getId() == null) {
            return createMon_an(mon_an);
        }
        Mon_an result = mon_anRepository.save(mon_an);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mon_an.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mon-ans : get all the mon_ans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mon_ans in body
     */
    @GetMapping("/mon-ans")
    @Timed
    public List<Mon_an> getAllMon_ans() {
        log.debug("REST request to get all Mon_ans");
        List<Mon_an> mon_ans = mon_anRepository.findAll();
        return mon_ans;
    }

    /**
     * GET  /mon-ans/:id : get the "id" mon_an.
     *
     * @param id the id of the mon_an to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mon_an, or with status 404 (Not Found)
     */
    @GetMapping("/mon-ans/{id}")
    @Timed
    public ResponseEntity<Mon_an> getMon_an(@PathVariable Long id) {
        log.debug("REST request to get Mon_an : {}", id);
        Mon_an mon_an = mon_anRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mon_an));
    }

    /**
     * DELETE  /mon-ans/:id : delete the "id" mon_an.
     *
     * @param id the id of the mon_an to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mon-ans/{id}")
    @Timed
    public ResponseEntity<Void> deleteMon_an(@PathVariable Long id) {
        log.debug("REST request to delete Mon_an : {}", id);
        mon_anRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @RequestMapping(value = "/mon-ans/theloai",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE )
    @Timed public List<Mon_an> getMonAn_byTheloai(@RequestParam String the_loai){
        List<Mon_an> result = mon_anRepository.findMonAntheloai(the_loai);
        return result ;
    }

    @GetMapping("/mon-ans/date")
    @Timed
    public List<Mon_an> MonAnDate() {
//        log.debug("REST request to get all Mon_ans date");
        List<Mon_an> mon_ans = mon_anRepository.findMonAndate();
        return mon_ans;
    }

    @RequestMapping(value = "/mon-ans/searchten",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed public List<Mon_an> searchbyTen(@RequestParam String ten){
        List<Mon_an> result = mon_anRepository.findMonAnTen(ten);
        return result;
    }
}
