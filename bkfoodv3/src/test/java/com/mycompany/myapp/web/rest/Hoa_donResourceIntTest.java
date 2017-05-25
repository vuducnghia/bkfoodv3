package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Bkfoodv3App;

import com.mycompany.myapp.domain.Hoa_don;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.Hoa_donRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Hoa_donResource REST controller.
 *
 * @see Hoa_donResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bkfoodv3App.class)
public class Hoa_donResourceIntTest {

    private static final String DEFAULT_TEN_MON_AN = "AAAAAAAAAA";
    private static final String UPDATED_TEN_MON_AN = "BBBBBBBBBB";

    private static final Integer DEFAULT_SALE = 0;
    private static final Integer UPDATED_SALE = 1;

    private static final Double DEFAULT_GIA_TIEN = 1D;
    private static final Double UPDATED_GIA_TIEN = 2D;

    private static final Integer DEFAULT_SO_LUONG = 1;
    private static final Integer UPDATED_SO_LUONG = 2;

    @Autowired
    private Hoa_donRepository hoa_donRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHoa_donMockMvc;

    private Hoa_don hoa_don;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Hoa_donResource hoa_donResource = new Hoa_donResource(hoa_donRepository);
        this.restHoa_donMockMvc = MockMvcBuilders.standaloneSetup(hoa_donResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hoa_don createEntity(EntityManager em) {
        Hoa_don hoa_don = new Hoa_don()
            .ten_mon_an(DEFAULT_TEN_MON_AN)
            .sale(DEFAULT_SALE)
            .gia_tien(DEFAULT_GIA_TIEN)
            .so_luong(DEFAULT_SO_LUONG);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        hoa_don.setUser(user);
        return hoa_don;
    }

    @Before
    public void initTest() {
        hoa_don = createEntity(em);
    }

    @Test
    @Transactional
    public void createHoa_don() throws Exception {
        int databaseSizeBeforeCreate = hoa_donRepository.findAll().size();

        // Create the Hoa_don
        restHoa_donMockMvc.perform(post("/api/hoa-dons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hoa_don)))
            .andExpect(status().isCreated());

        // Validate the Hoa_don in the database
        List<Hoa_don> hoa_donList = hoa_donRepository.findAll();
        assertThat(hoa_donList).hasSize(databaseSizeBeforeCreate + 1);
        Hoa_don testHoa_don = hoa_donList.get(hoa_donList.size() - 1);
        assertThat(testHoa_don.getTen_mon_an()).isEqualTo(DEFAULT_TEN_MON_AN);
        assertThat(testHoa_don.getSale()).isEqualTo(DEFAULT_SALE);
        assertThat(testHoa_don.getGia_tien()).isEqualTo(DEFAULT_GIA_TIEN);
        assertThat(testHoa_don.getSo_luong()).isEqualTo(DEFAULT_SO_LUONG);
    }

    @Test
    @Transactional
    public void createHoa_donWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hoa_donRepository.findAll().size();

        // Create the Hoa_don with an existing ID
        hoa_don.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoa_donMockMvc.perform(post("/api/hoa-dons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hoa_don)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Hoa_don> hoa_donList = hoa_donRepository.findAll();
        assertThat(hoa_donList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHoa_dons() throws Exception {
        // Initialize the database
        hoa_donRepository.saveAndFlush(hoa_don);

        // Get all the hoa_donList
        restHoa_donMockMvc.perform(get("/api/hoa-dons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoa_don.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten_mon_an").value(hasItem(DEFAULT_TEN_MON_AN.toString())))
            .andExpect(jsonPath("$.[*].sale").value(hasItem(DEFAULT_SALE)))
            .andExpect(jsonPath("$.[*].gia_tien").value(hasItem(DEFAULT_GIA_TIEN.doubleValue())))
            .andExpect(jsonPath("$.[*].so_luong").value(hasItem(DEFAULT_SO_LUONG)));
    }

    @Test
    @Transactional
    public void getHoa_don() throws Exception {
        // Initialize the database
        hoa_donRepository.saveAndFlush(hoa_don);

        // Get the hoa_don
        restHoa_donMockMvc.perform(get("/api/hoa-dons/{id}", hoa_don.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hoa_don.getId().intValue()))
            .andExpect(jsonPath("$.ten_mon_an").value(DEFAULT_TEN_MON_AN.toString()))
            .andExpect(jsonPath("$.sale").value(DEFAULT_SALE))
            .andExpect(jsonPath("$.gia_tien").value(DEFAULT_GIA_TIEN.doubleValue()))
            .andExpect(jsonPath("$.so_luong").value(DEFAULT_SO_LUONG));
    }

    @Test
    @Transactional
    public void getNonExistingHoa_don() throws Exception {
        // Get the hoa_don
        restHoa_donMockMvc.perform(get("/api/hoa-dons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoa_don() throws Exception {
        // Initialize the database
        hoa_donRepository.saveAndFlush(hoa_don);
        int databaseSizeBeforeUpdate = hoa_donRepository.findAll().size();

        // Update the hoa_don
        Hoa_don updatedHoa_don = hoa_donRepository.findOne(hoa_don.getId());
        updatedHoa_don
            .ten_mon_an(UPDATED_TEN_MON_AN)
            .sale(UPDATED_SALE)
            .gia_tien(UPDATED_GIA_TIEN)
            .so_luong(UPDATED_SO_LUONG);

        restHoa_donMockMvc.perform(put("/api/hoa-dons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHoa_don)))
            .andExpect(status().isOk());

        // Validate the Hoa_don in the database
        List<Hoa_don> hoa_donList = hoa_donRepository.findAll();
        assertThat(hoa_donList).hasSize(databaseSizeBeforeUpdate);
        Hoa_don testHoa_don = hoa_donList.get(hoa_donList.size() - 1);
        assertThat(testHoa_don.getTen_mon_an()).isEqualTo(UPDATED_TEN_MON_AN);
        assertThat(testHoa_don.getSale()).isEqualTo(UPDATED_SALE);
        assertThat(testHoa_don.getGia_tien()).isEqualTo(UPDATED_GIA_TIEN);
        assertThat(testHoa_don.getSo_luong()).isEqualTo(UPDATED_SO_LUONG);
    }

    @Test
    @Transactional
    public void updateNonExistingHoa_don() throws Exception {
        int databaseSizeBeforeUpdate = hoa_donRepository.findAll().size();

        // Create the Hoa_don

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHoa_donMockMvc.perform(put("/api/hoa-dons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hoa_don)))
            .andExpect(status().isCreated());

        // Validate the Hoa_don in the database
        List<Hoa_don> hoa_donList = hoa_donRepository.findAll();
        assertThat(hoa_donList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHoa_don() throws Exception {
        // Initialize the database
        hoa_donRepository.saveAndFlush(hoa_don);
        int databaseSizeBeforeDelete = hoa_donRepository.findAll().size();

        // Get the hoa_don
        restHoa_donMockMvc.perform(delete("/api/hoa-dons/{id}", hoa_don.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hoa_don> hoa_donList = hoa_donRepository.findAll();
        assertThat(hoa_donList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hoa_don.class);
    }
}
