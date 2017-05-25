package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Bkfoodv3App;

import com.mycompany.myapp.domain.Mon_an;
import com.mycompany.myapp.repository.Mon_anRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Mon_anResource REST controller.
 *
 * @see Mon_anResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bkfoodv3App.class)
public class Mon_anResourceIntTest {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final Double DEFAULT_GIA_TIEN = 1D;
    private static final Double UPDATED_GIA_TIEN = 2D;

    private static final String DEFAULT_THE_LOAI = "AAAAAAAAAA";
    private static final String UPDATED_THE_LOAI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_HINH_ANH = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_HINH_ANH = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_HINH_ANH_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_HINH_ANH_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_SALE = 0;
    private static final Integer UPDATED_SALE = 1;

    private static final String DEFAULT_LINK_COMMENT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_LINK_COMMENT_FACEBOOK = "BBBBBBBBBB";

    @Autowired
    private Mon_anRepository mon_anRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMon_anMockMvc;

    private Mon_an mon_an;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mon_anResource mon_anResource = new Mon_anResource(mon_anRepository);
        this.restMon_anMockMvc = MockMvcBuilders.standaloneSetup(mon_anResource)
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
    public static Mon_an createEntity(EntityManager em) {
        Mon_an mon_an = new Mon_an()
            .ten(DEFAULT_TEN)
            .gia_tien(DEFAULT_GIA_TIEN)
            .the_loai(DEFAULT_THE_LOAI)
            .date(DEFAULT_DATE)
            .hinh_anh(DEFAULT_HINH_ANH)
            .hinh_anhContentType(DEFAULT_HINH_ANH_CONTENT_TYPE)
            .sale(DEFAULT_SALE)
            .link_comment_facebook(DEFAULT_LINK_COMMENT_FACEBOOK);
        return mon_an;
    }

    @Before
    public void initTest() {
        mon_an = createEntity(em);
    }

    @Test
    @Transactional
    public void createMon_an() throws Exception {
        int databaseSizeBeforeCreate = mon_anRepository.findAll().size();

        // Create the Mon_an
        restMon_anMockMvc.perform(post("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isCreated());

        // Validate the Mon_an in the database
        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeCreate + 1);
        Mon_an testMon_an = mon_anList.get(mon_anList.size() - 1);
        assertThat(testMon_an.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testMon_an.getGia_tien()).isEqualTo(DEFAULT_GIA_TIEN);
        assertThat(testMon_an.getThe_loai()).isEqualTo(DEFAULT_THE_LOAI);
        assertThat(testMon_an.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMon_an.getHinh_anh()).isEqualTo(DEFAULT_HINH_ANH);
        assertThat(testMon_an.getHinh_anhContentType()).isEqualTo(DEFAULT_HINH_ANH_CONTENT_TYPE);
        assertThat(testMon_an.getSale()).isEqualTo(DEFAULT_SALE);
        assertThat(testMon_an.getLink_comment_facebook()).isEqualTo(DEFAULT_LINK_COMMENT_FACEBOOK);
    }

    @Test
    @Transactional
    public void createMon_anWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mon_anRepository.findAll().size();

        // Create the Mon_an with an existing ID
        mon_an.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMon_anMockMvc.perform(post("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = mon_anRepository.findAll().size();
        // set the field null
        mon_an.setTen(null);

        // Create the Mon_an, which fails.

        restMon_anMockMvc.perform(post("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isBadRequest());

        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGia_tienIsRequired() throws Exception {
        int databaseSizeBeforeTest = mon_anRepository.findAll().size();
        // set the field null
        mon_an.setGia_tien(null);

        // Create the Mon_an, which fails.

        restMon_anMockMvc.perform(post("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isBadRequest());

        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThe_loaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = mon_anRepository.findAll().size();
        // set the field null
        mon_an.setThe_loai(null);

        // Create the Mon_an, which fails.

        restMon_anMockMvc.perform(post("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isBadRequest());

        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mon_anRepository.findAll().size();
        // set the field null
        mon_an.setDate(null);

        // Create the Mon_an, which fails.

        restMon_anMockMvc.perform(post("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isBadRequest());

        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHinh_anhIsRequired() throws Exception {
        int databaseSizeBeforeTest = mon_anRepository.findAll().size();
        // set the field null
        mon_an.setHinh_anh(null);

        // Create the Mon_an, which fails.

        restMon_anMockMvc.perform(post("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isBadRequest());

        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = mon_anRepository.findAll().size();
        // set the field null
        mon_an.setSale(null);

        // Create the Mon_an, which fails.

        restMon_anMockMvc.perform(post("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isBadRequest());

        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLink_comment_facebookIsRequired() throws Exception {
        int databaseSizeBeforeTest = mon_anRepository.findAll().size();
        // set the field null
        mon_an.setLink_comment_facebook(null);

        // Create the Mon_an, which fails.

        restMon_anMockMvc.perform(post("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isBadRequest());

        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMon_ans() throws Exception {
        // Initialize the database
        mon_anRepository.saveAndFlush(mon_an);

        // Get all the mon_anList
        restMon_anMockMvc.perform(get("/api/mon-ans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mon_an.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN.toString())))
            .andExpect(jsonPath("$.[*].gia_tien").value(hasItem(DEFAULT_GIA_TIEN.doubleValue())))
            .andExpect(jsonPath("$.[*].the_loai").value(hasItem(DEFAULT_THE_LOAI.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].hinh_anhContentType").value(hasItem(DEFAULT_HINH_ANH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].hinh_anh").value(hasItem(Base64Utils.encodeToString(DEFAULT_HINH_ANH))))
            .andExpect(jsonPath("$.[*].sale").value(hasItem(DEFAULT_SALE)))
            .andExpect(jsonPath("$.[*].link_comment_facebook").value(hasItem(DEFAULT_LINK_COMMENT_FACEBOOK.toString())));
    }

    @Test
    @Transactional
    public void getMon_an() throws Exception {
        // Initialize the database
        mon_anRepository.saveAndFlush(mon_an);

        // Get the mon_an
        restMon_anMockMvc.perform(get("/api/mon-ans/{id}", mon_an.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mon_an.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN.toString()))
            .andExpect(jsonPath("$.gia_tien").value(DEFAULT_GIA_TIEN.doubleValue()))
            .andExpect(jsonPath("$.the_loai").value(DEFAULT_THE_LOAI.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.hinh_anhContentType").value(DEFAULT_HINH_ANH_CONTENT_TYPE))
            .andExpect(jsonPath("$.hinh_anh").value(Base64Utils.encodeToString(DEFAULT_HINH_ANH)))
            .andExpect(jsonPath("$.sale").value(DEFAULT_SALE))
            .andExpect(jsonPath("$.link_comment_facebook").value(DEFAULT_LINK_COMMENT_FACEBOOK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMon_an() throws Exception {
        // Get the mon_an
        restMon_anMockMvc.perform(get("/api/mon-ans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMon_an() throws Exception {
        // Initialize the database
        mon_anRepository.saveAndFlush(mon_an);
        int databaseSizeBeforeUpdate = mon_anRepository.findAll().size();

        // Update the mon_an
        Mon_an updatedMon_an = mon_anRepository.findOne(mon_an.getId());
        updatedMon_an
            .ten(UPDATED_TEN)
            .gia_tien(UPDATED_GIA_TIEN)
            .the_loai(UPDATED_THE_LOAI)
            .date(UPDATED_DATE)
            .hinh_anh(UPDATED_HINH_ANH)
            .hinh_anhContentType(UPDATED_HINH_ANH_CONTENT_TYPE)
            .sale(UPDATED_SALE)
            .link_comment_facebook(UPDATED_LINK_COMMENT_FACEBOOK);

        restMon_anMockMvc.perform(put("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMon_an)))
            .andExpect(status().isOk());

        // Validate the Mon_an in the database
        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeUpdate);
        Mon_an testMon_an = mon_anList.get(mon_anList.size() - 1);
        assertThat(testMon_an.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testMon_an.getGia_tien()).isEqualTo(UPDATED_GIA_TIEN);
        assertThat(testMon_an.getThe_loai()).isEqualTo(UPDATED_THE_LOAI);
        assertThat(testMon_an.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMon_an.getHinh_anh()).isEqualTo(UPDATED_HINH_ANH);
        assertThat(testMon_an.getHinh_anhContentType()).isEqualTo(UPDATED_HINH_ANH_CONTENT_TYPE);
        assertThat(testMon_an.getSale()).isEqualTo(UPDATED_SALE);
        assertThat(testMon_an.getLink_comment_facebook()).isEqualTo(UPDATED_LINK_COMMENT_FACEBOOK);
    }

    @Test
    @Transactional
    public void updateNonExistingMon_an() throws Exception {
        int databaseSizeBeforeUpdate = mon_anRepository.findAll().size();

        // Create the Mon_an

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMon_anMockMvc.perform(put("/api/mon-ans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mon_an)))
            .andExpect(status().isCreated());

        // Validate the Mon_an in the database
        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMon_an() throws Exception {
        // Initialize the database
        mon_anRepository.saveAndFlush(mon_an);
        int databaseSizeBeforeDelete = mon_anRepository.findAll().size();

        // Get the mon_an
        restMon_anMockMvc.perform(delete("/api/mon-ans/{id}", mon_an.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mon_an> mon_anList = mon_anRepository.findAll();
        assertThat(mon_anList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mon_an.class);
    }
}
