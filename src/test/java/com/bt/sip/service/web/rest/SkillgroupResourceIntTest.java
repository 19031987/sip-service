package com.bt.sip.service.web.rest;

import com.bt.sip.service.SipserviceApp;

import com.bt.sip.service.domain.Skillgroup;
import com.bt.sip.service.repository.SkillgroupRepository;
import com.bt.sip.service.service.SkillgroupService;
import com.bt.sip.service.web.rest.errors.ExceptionTranslator;

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


import static com.bt.sip.service.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SkillgroupResource REST controller.
 *
 * @see SkillgroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SipserviceApp.class)
public class SkillgroupResourceIntTest {

    @Autowired
    private SkillgroupRepository skillgroupRepository;

    @Autowired
    private SkillgroupService skillgroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSkillgroupMockMvc;

    private Skillgroup skillgroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillgroupResource skillgroupResource = new SkillgroupResource(skillgroupService);
        this.restSkillgroupMockMvc = MockMvcBuilders.standaloneSetup(skillgroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skillgroup createEntity(EntityManager em) {
        Skillgroup skillgroup = new Skillgroup();
        return skillgroup;
    }

    @Before
    public void initTest() {
        skillgroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillgroup() throws Exception {
        int databaseSizeBeforeCreate = skillgroupRepository.findAll().size();

        // Create the Skillgroup
        restSkillgroupMockMvc.perform(post("/api/skillgroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillgroup)))
            .andExpect(status().isCreated());

        // Validate the Skillgroup in the database
        List<Skillgroup> skillgroupList = skillgroupRepository.findAll();
        assertThat(skillgroupList).hasSize(databaseSizeBeforeCreate + 1);
        Skillgroup testSkillgroup = skillgroupList.get(skillgroupList.size() - 1);
    }

    @Test
    @Transactional
    public void createSkillgroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillgroupRepository.findAll().size();

        // Create the Skillgroup with an existing ID
        skillgroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillgroupMockMvc.perform(post("/api/skillgroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillgroup)))
            .andExpect(status().isBadRequest());

        // Validate the Skillgroup in the database
        List<Skillgroup> skillgroupList = skillgroupRepository.findAll();
        assertThat(skillgroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSkillgroups() throws Exception {
        // Initialize the database
        skillgroupRepository.saveAndFlush(skillgroup);

        // Get all the skillgroupList
        restSkillgroupMockMvc.perform(get("/api/skillgroups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillgroup.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSkillgroup() throws Exception {
        // Initialize the database
        skillgroupRepository.saveAndFlush(skillgroup);

        // Get the skillgroup
        restSkillgroupMockMvc.perform(get("/api/skillgroups/{id}", skillgroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillgroup.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSkillgroup() throws Exception {
        // Get the skillgroup
        restSkillgroupMockMvc.perform(get("/api/skillgroups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillgroup() throws Exception {
        // Initialize the database
        skillgroupService.save(skillgroup);

        int databaseSizeBeforeUpdate = skillgroupRepository.findAll().size();

        // Update the skillgroup
        Skillgroup updatedSkillgroup = skillgroupRepository.findById(skillgroup.getId()).get();
        // Disconnect from session so that the updates on updatedSkillgroup are not directly saved in db
        em.detach(updatedSkillgroup);

        restSkillgroupMockMvc.perform(put("/api/skillgroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillgroup)))
            .andExpect(status().isOk());

        // Validate the Skillgroup in the database
        List<Skillgroup> skillgroupList = skillgroupRepository.findAll();
        assertThat(skillgroupList).hasSize(databaseSizeBeforeUpdate);
        Skillgroup testSkillgroup = skillgroupList.get(skillgroupList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillgroup() throws Exception {
        int databaseSizeBeforeUpdate = skillgroupRepository.findAll().size();

        // Create the Skillgroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillgroupMockMvc.perform(put("/api/skillgroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillgroup)))
            .andExpect(status().isBadRequest());

        // Validate the Skillgroup in the database
        List<Skillgroup> skillgroupList = skillgroupRepository.findAll();
        assertThat(skillgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillgroup() throws Exception {
        // Initialize the database
        skillgroupService.save(skillgroup);

        int databaseSizeBeforeDelete = skillgroupRepository.findAll().size();

        // Get the skillgroup
        restSkillgroupMockMvc.perform(delete("/api/skillgroups/{id}", skillgroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Skillgroup> skillgroupList = skillgroupRepository.findAll();
        assertThat(skillgroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skillgroup.class);
        Skillgroup skillgroup1 = new Skillgroup();
        skillgroup1.setId(1L);
        Skillgroup skillgroup2 = new Skillgroup();
        skillgroup2.setId(skillgroup1.getId());
        assertThat(skillgroup1).isEqualTo(skillgroup2);
        skillgroup2.setId(2L);
        assertThat(skillgroup1).isNotEqualTo(skillgroup2);
        skillgroup1.setId(null);
        assertThat(skillgroup1).isNotEqualTo(skillgroup2);
    }
}
