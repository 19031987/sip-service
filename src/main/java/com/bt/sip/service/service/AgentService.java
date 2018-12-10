package com.bt.sip.service.service;

import com.bt.sip.service.domain.Agent;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Agent.
 */
public interface AgentService {

    /**
     * Save a agent.
     *
     * @param agent the entity to save
     * @return the persisted entity
     */
    Agent save(Agent agent);

    /**
     * Get all the agents.
     *
     * @return the list of entities
     */
    List<Agent> findAll();


    /**
     * Get the "id" agent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Agent> findOne(Long id);

    /**
     * Delete the "id" agent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
