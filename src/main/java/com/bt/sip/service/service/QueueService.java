package com.bt.sip.service.service;

import com.bt.sip.service.domain.Queue;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Queue.
 */
public interface QueueService {

    /**
     * Save a queue.
     *
     * @param queue the entity to save
     * @return the persisted entity
     */
    Queue save(Queue queue);

    /**
     * Get all the queues.
     *
     * @return the list of entities
     */
    List<Queue> findAll();


    /**
     * Get the "id" queue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Queue> findOne(Long id);

    /**
     * Delete the "id" queue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
