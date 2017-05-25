package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Hoa_don;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.Hoa_donRepository;
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
 * REST controller for managing Hoa_don.
 */
@RestController
@RequestMapping("/api")
public class Hoa_donResource {

    private final Logger log = LoggerFactory.getLogger(Hoa_donResource.class);

    private static final String ENTITY_NAME = "hoa_don";

    private final Hoa_donRepository hoa_donRepository;

    public Hoa_donResource(Hoa_donRepository hoa_donRepository) {
        this.hoa_donRepository = hoa_donRepository;
    }

    /**
     * POST  /hoa-dons : Create a new hoa_don.
     *
     * @param hoa_don the hoa_don to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hoa_don, or with status 400 (Bad Request) if the hoa_don has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hoa-dons")
    @Timed
    public ResponseEntity<Hoa_don> createHoa_don(@Valid @RequestBody Hoa_don hoa_don) throws URISyntaxException {
        log.debug("REST request to save Hoa_don : {}", hoa_don);
        if (hoa_don.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hoa_don cannot already have an ID")).body(null);
        }
        Hoa_don result = hoa_donRepository.save(hoa_don);
        return ResponseEntity.created(new URI("/api/hoa-dons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hoa-dons : Updates an existing hoa_don.
     *
     * @param hoa_don the hoa_don to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hoa_don,
     * or with status 400 (Bad Request) if the hoa_don is not valid,
     * or with status 500 (Internal Server Error) if the hoa_don couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hoa-dons")
    @Timed
    public ResponseEntity<Hoa_don> updateHoa_don(@Valid @RequestBody Hoa_don hoa_don) throws URISyntaxException {
        log.debug("REST request to update Hoa_don : {}", hoa_don);
        if (hoa_don.getId() == null) {
            return createHoa_don(hoa_don);
        }
        Hoa_don result = hoa_donRepository.save(hoa_don);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hoa_don.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hoa-dons : get all the hoa_dons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hoa_dons in body
     */
    @GetMapping("/hoa-dons")
    @Timed
    public List<Hoa_don> getAllHoa_dons() {
        log.debug("REST request to get all Hoa_dons");
        List<Hoa_don> hoa_dons = hoa_donRepository.findAll();
        return hoa_dons;
    }

    /**
     * GET  /hoa-dons/:id : get the "id" hoa_don.
     *
     * @param id the id of the hoa_don to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hoa_don, or with status 404 (Not Found)
     */
    @GetMapping("/hoa-dons/{id}")
    @Timed
    public ResponseEntity<Hoa_don> getHoa_don(@PathVariable Long id) {
        log.debug("REST request to get Hoa_don : {}", id);
        Hoa_don hoa_don = hoa_donRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hoa_don));
    }

    /**
     * DELETE  /hoa-dons/:id : delete the "id" hoa_don.
     *
     * @param id the id of the hoa_don to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hoa-dons/{id}")
    @Timed
    public ResponseEntity<Void> deleteHoa_don(@PathVariable Long id) {
        log.debug("REST request to delete Hoa_don : {}", id);
        hoa_donRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(value = "/hoa-dons/searchtenmonan",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed public List<Hoa_don> searchbyTen(@RequestParam String ten){
        List<Hoa_don> result = hoa_donRepository.findByMonAnInHoaDon(ten);
        return result;
    }

    @RequestMapping(value = "/hoa-dons/searchHoaDonbyUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed public List<Hoa_don> searchbyUser(@RequestParam String user){
        List<Hoa_don> result = hoa_donRepository.findHoaDonByUser(user);
        return result;
    }

    @RequestMapping(value = "/hoa-dons/TinhTienHoaDonbyUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String TinhTienbyUser(@RequestParam String user){
        List<Hoa_don> result = hoa_donRepository.findHoaDonByUser(user);
        double tong = 0;
        for(int i = 0; i < result.size(); ++i){
            tong += result.get(i).getGia_tien() * (100 - result.get(i).getSale()) / 100 * result.get(i).getSo_luong();
            System.out.print(tong);
        }
        return "{ \"message\":" + tong + "}";
    }

}
