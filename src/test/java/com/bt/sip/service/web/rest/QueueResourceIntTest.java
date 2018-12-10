package com.bt.sip.service.web.rest;

import com.bt.sip.service.SipserviceApp;

import com.bt.sip.service.domain.Queue;
import com.bt.sip.service.repository.QueueRepository;
import com.bt.sip.service.service.QueueService;
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
 * Test class for the QueueResource REST controller.
 *
 * @see QueueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SipserviceApp.class)
public class QueueResourceIntTest {

    private static final String DEFAULT_QUEUENAME = "AAAAAAAAAA";
    private static final String UPDATED_QUEUENAME = "BBBBBBBBBB";

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueService queueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQueueMockMvc;

    private Queue queue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QueueResource queueResource = new QueueResource(queueService);
        this.restQueueMockMvc = MockMvcBuilders.standaloneSetup(queueResource)
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
    public static Queue createEntity(EntityManager em) {
        Queue queue = new Queue()
            .queuename(DEFAULT_QUEUENAME);
        return queue;
    }

    @Before
    public void initTest() {
        queue = createEntity(em);
    }

    @Test
    @Transactional
    public void createQueue() throws Exception {
        int databaseSizeBeforeCreate = queueRepository.findAll().size();

        // Create the Queue
        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isCreated());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeCreate + 1);
        Queue testQueue = queueList.get(queueList.size() - 1);
        assertThat(testQueue.getQueuename()).isEqualTo(DEFAULT_QUEUENAME);
    }

    @Test
    @Transactional
    public void createQueueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = queueRepository.findAll().size();

        // Create the Queue with an existing ID
        queue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isBadRequest());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQueues() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);

        // Get all the queueList
        restQueueMockMvc.perform(get("/api/queues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queue.getId().intValue())))
            .andExpect(jsonPath("$.[*].queuename").value(hasItem(DEFAULT_QUEUENAME.toString())));
    }
    
    @Test
    @Transactional
    public void getQueue() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);

        // Get the queue
        restQueueMockMvc.perform(get("/api/queues/{id}", queue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(queue.getId().intValue()))
            .andExpect(jsonPath("$.queuename").value(DEFAULT_QUEUENAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQueue() throws Exception {
        // Get the queue
        restQueueMockMvc.perform(get("/api/queues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQueue() throws Exception {
        // Initialize the database
        queueService.save(queue);

        int databaseSizeBeforeUpdate = queueRepository.findAll().size();

        // Update the queue
        Queue updatedQueue = queueRepository.findById(queue.getId()).get();
        // Disconnect from session so that the updates on updatedQueue are not directly saved in db
        em.detach(updatedQueue);
        updatedQueue
            .queuename(UPDATED_QUEUENAME);

        restQueueMockMvc.perform(put("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQueue)))
            .andExpect(status().isOk());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeUpdate);
        Queue testQueue = queueList.get(queueList.size() - 1);
        assertThat(testQueue.getQueuename()).isEqualTo(UPDATED_QUEUENAME);
    }

    @Test
    @Transactional
    public void updateNonExistingQueue() throws Exception {
        int databaseSizeBeforeUpdate = queueRepository.findAll().size();

        // Create the Queue

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQueueMockMvc.perform(put("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isBadRequest());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQueue() throws Exception {
        // Initialize the database
        queueService.save(queue);

        int databaseSizeBeforeDelete = queueRepository.findAll().size();

        // Get the queue
        restQueueMockMvc.perform(delete("/api/queues/{id}", queue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Queue.class);
        Queue queue1 = new Queue();
        queue1.setId(1L);
        Queue queue2 = new Queue();
        queue2.setId(queue1.getId());
        assertThat(queue1).isEqualTo(queue2);
        queue2.setId(2L);
        assertThat(queue1).isNotEqualTo(queue2);
        queue1.setId(null);
        assertThat(queue1).isNotEqualTo(queue2);
    }
}
