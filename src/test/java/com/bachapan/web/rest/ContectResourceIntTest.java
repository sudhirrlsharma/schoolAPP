package com.bachapan.web.rest;

import com.bachapan.Application;
import com.bachapan.domain.Contect;
import com.bachapan.repository.ContectRepository;
import com.bachapan.service.ContectService;
import com.bachapan.web.rest.dto.ContectDTO;
import com.bachapan.web.rest.mapper.ContectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ContectResource REST controller.
 *
 * @see ContectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContectResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_IM = "AAAAA";
    private static final String UPDATED_IM = "BBBBB";
    private static final String DEFAULT_ADDRESS1 = "AAAAA";
    private static final String UPDATED_ADDRESS1 = "BBBBB";
    private static final String DEFAULT_ADRESS2 = "AAAAA";
    private static final String UPDATED_ADRESS2 = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_STATE = "AAAAA";
    private static final String UPDATED_STATE = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";
    private static final String DEFAULT_PIN_CODE = "AAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBB";

    @Inject
    private ContectRepository contectRepository;

    @Inject
    private ContectMapper contectMapper;

    @Inject
    private ContectService contectService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContectMockMvc;

    private Contect contect;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContectResource contectResource = new ContectResource();
        ReflectionTestUtils.setField(contectResource, "contectService", contectService);
        ReflectionTestUtils.setField(contectResource, "contectMapper", contectMapper);
        this.restContectMockMvc = MockMvcBuilders.standaloneSetup(contectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contect = new Contect();
        contect.setName(DEFAULT_NAME);
        contect.setPhone(DEFAULT_PHONE);
        contect.setEmail(DEFAULT_EMAIL);
        contect.setIm(DEFAULT_IM);
        contect.setAddress1(DEFAULT_ADDRESS1);
        contect.setAdress2(DEFAULT_ADRESS2);
        contect.setCity(DEFAULT_CITY);
        contect.setState(DEFAULT_STATE);
        contect.setCountry(DEFAULT_COUNTRY);
        contect.setPinCode(DEFAULT_PIN_CODE);
    }

    @Test
    @Transactional
    public void createContect() throws Exception {
        int databaseSizeBeforeCreate = contectRepository.findAll().size();

        // Create the Contect
        ContectDTO contectDTO = contectMapper.contectToContectDTO(contect);

        restContectMockMvc.perform(post("/api/contects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contectDTO)))
                .andExpect(status().isCreated());

        // Validate the Contect in the database
        List<Contect> contects = contectRepository.findAll();
        assertThat(contects).hasSize(databaseSizeBeforeCreate + 1);
        Contect testContect = contects.get(contects.size() - 1);
        assertThat(testContect.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContect.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testContect.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContect.getIm()).isEqualTo(DEFAULT_IM);
        assertThat(testContect.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testContect.getAdress2()).isEqualTo(DEFAULT_ADRESS2);
        assertThat(testContect.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testContect.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testContect.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testContect.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
    }

    @Test
    @Transactional
    public void getAllContects() throws Exception {
        // Initialize the database
        contectRepository.saveAndFlush(contect);

        // Get all the contects
        restContectMockMvc.perform(get("/api/contects?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contect.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].im").value(hasItem(DEFAULT_IM.toString())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].adress2").value(hasItem(DEFAULT_ADRESS2.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE.toString())));
    }

    @Test
    @Transactional
    public void getContect() throws Exception {
        // Initialize the database
        contectRepository.saveAndFlush(contect);

        // Get the contect
        restContectMockMvc.perform(get("/api/contects/{id}", contect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contect.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.im").value(DEFAULT_IM.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.adress2").value(DEFAULT_ADRESS2.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContect() throws Exception {
        // Get the contect
        restContectMockMvc.perform(get("/api/contects/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContect() throws Exception {
        // Initialize the database
        contectRepository.saveAndFlush(contect);

		int databaseSizeBeforeUpdate = contectRepository.findAll().size();

        // Update the contect
        contect.setName(UPDATED_NAME);
        contect.setPhone(UPDATED_PHONE);
        contect.setEmail(UPDATED_EMAIL);
        contect.setIm(UPDATED_IM);
        contect.setAddress1(UPDATED_ADDRESS1);
        contect.setAdress2(UPDATED_ADRESS2);
        contect.setCity(UPDATED_CITY);
        contect.setState(UPDATED_STATE);
        contect.setCountry(UPDATED_COUNTRY);
        contect.setPinCode(UPDATED_PIN_CODE);
        ContectDTO contectDTO = contectMapper.contectToContectDTO(contect);

        restContectMockMvc.perform(put("/api/contects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contectDTO)))
                .andExpect(status().isOk());

        // Validate the Contect in the database
        List<Contect> contects = contectRepository.findAll();
        assertThat(contects).hasSize(databaseSizeBeforeUpdate);
        Contect testContect = contects.get(contects.size() - 1);
        assertThat(testContect.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContect.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testContect.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContect.getIm()).isEqualTo(UPDATED_IM);
        assertThat(testContect.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testContect.getAdress2()).isEqualTo(UPDATED_ADRESS2);
        assertThat(testContect.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContect.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testContect.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testContect.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    public void deleteContect() throws Exception {
        // Initialize the database
        contectRepository.saveAndFlush(contect);

		int databaseSizeBeforeDelete = contectRepository.findAll().size();

        // Get the contect
        restContectMockMvc.perform(delete("/api/contects/{id}", contect.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contect> contects = contectRepository.findAll();
        assertThat(contects).hasSize(databaseSizeBeforeDelete - 1);
    }
}
