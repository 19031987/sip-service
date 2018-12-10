package com.bt.sip.service.service.impl;

import com.bt.sip.service.service.QueueService;
import com.bt.sip.service.domain.Queue;
import com.bt.sip.service.repository.QueueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Queue.
 */
@Service
@Transactional
public class QueueServiceImpl implements QueueService {

    private final Logger log = LoggerFactory.getLogger(QueueServiceImpl.class);

    private final QueueRepository queueRepository;

    public QueueServiceImpl(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    /**
     * Save a queue.
     *
     * @param queue the entity to save
     * @return the persisted entity
     */
    @Override
    public Queue save(Queue queue) {
        log.debug("Request to save Queue : {}", queue);
        return queueRepository.save(queue);
    }

    /**
     * Get all the queues.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Queue> findAll() {
        log.debug("Request to get all Queues");
        return queueRepository.findAll();
    }


    /**
     * Get one queue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Queue> findOne(Long id) {
        log.debug("Request to get Queue : {}", id);
        return queueRepository.findById(id);
    }

    /**
     * Delete the queue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Queue : {}", id);
        queueRepository.deleteById(id);
    }
}
