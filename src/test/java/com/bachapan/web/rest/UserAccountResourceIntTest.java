package com.bachapan.web.rest;

import com.bachapan.Application;
import com.bachapan.domain.UserAccount;
import com.bachapan.repository.UserAccountRepository;
import com.bachapan.service.UserAccountService;
import com.bachapan.web.rest.dto.UserAccountDTO;
import com.bachapan.web.rest.mapper.UserAccountMapper;

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
 * Test class for the UserAccountResource REST controller.
 *
 * @see UserAccountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserAccountResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAA";
    private static final String UPDATED_USER_ID = "BBBBB";
    private static final String DEFAULT_PASSWORD = "AAAAA";
    private static final String UPDATED_PASSWORD = "BBBBB";

    @Inject
    private UserAccountRepository userAccountRepository;

    @Inject
    private UserAccountMapper userAccountMapper;

    @Inject
    private UserAccountService userAccountService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUserAccountMockMvc;

    private UserAccount userAccount;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserAccountResource userAccountResource = new UserAccountResource();
        ReflectionTestUtils.setField(userAccountResource, "userAccountService", userAccountService);
        ReflectionTestUtils.setField(userAccountResource, "userAccountMapper", userAccountMapper);
        this.restUserAccountMockMvc = MockMvcBuilders.standaloneSetup(userAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        userAccount = new UserAccount();
        userAccount.setUserId(DEFAULT_USER_ID);
        userAccount.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createUserAccount() throws Exception {
        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();

        // Create the UserAccount
        UserAccountDTO userAccountDTO = userAccountMapper.userAccountToUserAccountDTO(userAccount);

        restUserAccountMockMvc.perform(post("/api/userAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
                .andExpect(status().isCreated());

        // Validate the UserAccount in the database
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        assertThat(userAccounts).hasSize(databaseSizeBeforeCreate + 1);
        UserAccount testUserAccount = userAccounts.get(userAccounts.size() - 1);
        assertThat(testUserAccount.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserAccount.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllUserAccounts() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get all the userAccounts
        restUserAccountMockMvc.perform(get("/api/userAccounts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userAccount.getId().intValue())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get the userAccount
        restUserAccountMockMvc.perform(get("/api/userAccounts/{id}", userAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userAccount.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAccount() throws Exception {
        // Get the userAccount
        restUserAccountMockMvc.perform(get("/api/userAccounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

		int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount
        userAccount.setUserId(UPDATED_USER_ID);
        userAccount.setPassword(UPDATED_PASSWORD);
        UserAccountDTO userAccountDTO = userAccountMapper.userAccountToUserAccountDTO(userAccount);

        restUserAccountMockMvc.perform(put("/api/userAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
                .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        assertThat(userAccounts).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccounts.get(userAccounts.size() - 1);
        assertThat(testUserAccount.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void deleteUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

		int databaseSizeBeforeDelete = userAccountRepository.findAll().size();

        // Get the userAccount
        restUserAccountMockMvc.perform(delete("/api/userAccounts/{id}", userAccount.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        assertThat(userAccounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
