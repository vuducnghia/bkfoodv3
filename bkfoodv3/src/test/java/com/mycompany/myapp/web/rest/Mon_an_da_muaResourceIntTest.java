package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Bkfoodv3App;

import com.mycompany.myapp.domain.Mon_an_da_mua;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.Mon_an_da_muaRepository;
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
 * Test class for the Mon_an_da_muaResource REST controller.
 *
 * @see Mon_an_da_muaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bkfoodv3App.class)
public class Mon_an_da_muaResourceIntTest {

    private static final String DEFAULT_TEN_MON_AN = "AAAAAAAAAA";
    private static final String UPDATED_TEN_MON_AN = "BBBBBBBBBB";

    private static final Integer DEFAULT_SO_LUONG = 1;
    private static final Integer UPDATED_SO_LUONG = 2;

    private static final Integer DEFAULT_SALE = 1;
    private static final Integer UPDATED_SALE = 2;

    private static final Double DEFAULT_GIA_TIEN = 1D;
    private static final Double UPDATED_GIA_TIEN = 2D;

    @Autowired
    private Mon_an_da_muaRepository mon_an_da_muaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMon_an_da_muaMockMvc;

    private Mon_an_da_mua mon_an_da_mua;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mon_an_da_muaResource mon_an_da_muaResource = new Mon_an_da_muaResource(mon_an_da_muaRepository);
        this.restMon_an_da_muaMockMvc = MockMvcBuilders.standaloneSetup(mon_an_da_muaResource)
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
    public static Mon_an_da_mua createEntity(EntityManager em) {
        Mon_an_da_mua mon_an_da_mua = new Mon_an_da_mua()
            .ten_mon_an(DEFAULT_TEN_MON_AN)
            .so_luong(DEFAULT_SO_LUONG)
            .sale(DEFAULT_SALE)
            .gia_tien(DEFAULT_GIA_TIEN);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        mon_an_da_mua.setUser(user);
        return mon_an_da_mua;
    }

    @Before
    public void initTest() {
        mon_an_da_mua = createEntity(em);
    }

    @Test
    @Transactional
    public void createMon_an_da_mua() throws Exception {
        int databaseSizeBeforeCreate = mon_an_da_muaRepository.findAll().size();

        // Create the Mon_an_da_mua
        restMon_an_da_muaMockMvc.perform(post("/api/mon-an-da-muas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an_da_mua)))
            .andExpect(status().isCreated());

        // Validate the Mon_an_da_mua in the database
        List<Mon_an_da_mua> mon_an_da_muaList = mon_an_da_muaRepository.findAll();
        assertThat(mon_an_da_muaList).hasSize(databaseSizeBeforeCreate + 1);
        Mon_an_da_mua testMon_an_da_mua = mon_an_da_muaList.get(mon_an_da_muaList.size() - 1);
        assertThat(testMon_an_da_mua.getTen_mon_an()).isEqualTo(DEFAULT_TEN_MON_AN);
        assertThat(testMon_an_da_mua.getSo_luong()).isEqualTo(DEFAULT_SO_LUONG);
        assertThat(testMon_an_da_mua.getSale()).isEqualTo(DEFAULT_SALE);
        assertThat(testMon_an_da_mua.getGia_tien()).isEqualTo(DEFAULT_GIA_TIEN);
    }

    @Test
    @Transactional
    public void createMon_an_da_muaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mon_an_da_muaRepository.findAll().size();

        // Create the Mon_an_da_mua with an existing ID
        mon_an_da_mua.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMon_an_da_muaMockMvc.perform(post("/api/mon-an-da-muas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an_da_mua)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Mon_an_da_mua> mon_an_da_muaList = mon_an_da_muaRepository.findAll();
        assertThat(mon_an_da_muaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMon_an_da_muas() throws Exception {
        // Initialize the database
        mon_an_da_muaRepository.saveAndFlush(mon_an_da_mua);

        // Get all the mon_an_da_muaList
        restMon_an_da_muaMockMvc.perform(get("/api/mon-an-da-muas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mon_an_da_mua.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten_mon_an").value(hasItem(DEFAULT_TEN_MON_AN.toString())))
            .andExpect(jsonPath("$.[*].so_luong").value(hasItem(DEFAULT_SO_LUONG)))
            .andExpect(jsonPath("$.[*].sale").value(hasItem(DEFAULT_SALE)))
            .andExpect(jsonPath("$.[*].gia_tien").value(hasItem(DEFAULT_GIA_TIEN.doubleValue())));
    }

    @Test
    @Transactional
    public void getMon_an_da_mua() throws Exception {
        // Initialize the database
        mon_an_da_muaRepository.saveAndFlush(mon_an_da_mua);

        // Get the mon_an_da_mua
        restMon_an_da_muaMockMvc.perform(get("/api/mon-an-da-muas/{id}", mon_an_da_mua.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mon_an_da_mua.getId().intValue()))
            .andExpect(jsonPath("$.ten_mon_an").value(DEFAULT_TEN_MON_AN.toString()))
            .andExpect(jsonPath("$.so_luong").value(DEFAULT_SO_LUONG))
            .andExpect(jsonPath("$.sale").value(DEFAULT_SALE))
            .andExpect(jsonPath("$.gia_tien").value(DEFAULT_GIA_TIEN.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMon_an_da_mua() throws Exception {
        // Get the mon_an_da_mua
        restMon_an_da_muaMockMvc.perform(get("/api/mon-an-da-muas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMon_an_da_mua() throws Exception {
        // Initialize the database
        mon_an_da_muaRepository.saveAndFlush(mon_an_da_mua);
        int databaseSizeBeforeUpdate = mon_an_da_muaRepository.findAll().size();

        // Update the mon_an_da_mua
        Mon_an_da_mua updatedMon_an_da_mua = mon_an_da_muaRepository.findOne(mon_an_da_mua.getId());
        updatedMon_an_da_mua
            .ten_mon_an(UPDATED_TEN_MON_AN)
            .so_luong(UPDATED_SO_LUONG)
            .sale(UPDATED_SALE)
            .gia_tien(UPDATED_GIA_TIEN);

        restMon_an_da_muaMockMvc.perform(put("/api/mon-an-da-muas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMon_an_da_mua)))
            .andExpect(status().isOk());

        // Validate the Mon_an_da_mua in the database
        List<Mon_an_da_mua> mon_an_da_muaList = mon_an_da_muaRepository.findAll();
        assertThat(mon_an_da_muaList).hasSize(databaseSizeBeforeUpdate);
        Mon_an_da_mua testMon_an_da_mua = mon_an_da_muaList.get(mon_an_da_muaList.size() - 1);
        assertThat(testMon_an_da_mua.getTen_mon_an()).isEqualTo(UPDATED_TEN_MON_AN);
        assertThat(testMon_an_da_mua.getSo_luong()).isEqualTo(UPDATED_SO_LUONG);
        assertThat(testMon_an_da_mua.getSale()).isEqualTo(UPDATED_SALE);
        assertThat(testMon_an_da_mua.getGia_tien()).isEqualTo(UPDATED_GIA_TIEN);
    }

    @Test
    @Transactional
    public void updateNonExistingMon_an_da_mua() throws Exception {
        int databaseSizeBeforeUpdate = mon_an_da_muaRepository.findAll().size();

        // Create the Mon_an_da_mua

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMon_an_da_muaMockMvc.perform(put("/api/mon-an-da-muas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an_da_mua)))
            .andExpect(status().isCreated());

        // Validate the Mon_an_da_mua in the database
        List<Mon_an_da_mua> mon_an_da_muaList = mon_an_da_muaRepository.findAll();
        assertThat(mon_an_da_muaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMon_an_da_mua() throws Exception {
        // Initialize the database
        mon_an_da_muaRepository.saveAndFlush(mon_an_da_mua);
        int databaseSizeBeforeDelete = mon_an_da_muaRepository.findAll().size();

        // Get the mon_an_da_mua
        restMon_an_da_muaMockMvc.perform(delete("/api/mon-an-da-muas/{id}", mon_an_da_mua.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mon_an_da_mua> mon_an_da_muaList = mon_an_da_muaRepository.findAll();
        assertThat(mon_an_da_muaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mon_an_da_mua.class);
    }
}
