package com.bt.sip.service.service.impl;

import com.bt.sip.service.service.AgentService;
import com.bt.sip.service.domain.Agent;
import com.bt.sip.service.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Agent.
 */
@Service
@Transactional
public class AgentServiceImpl implements AgentService {

    private final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    /**
     * Save a agent.
     *
     * @param agent the entity to save
     * @return the persisted entity
     */
    @Override
    public Agent save(Agent agent) {
        log.debug("Request to save Agent : {}", agent);
        return agentRepository.save(agent);
    }

    /**
     * Get all the agents.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Agent> findAll() {
        log.debug("Request to get all Agents");
        return agentRepository.findAll();
    }


    /**
     * Get one agent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Agent> findOne(Long id) {
        log.debug("Request to get Agent : {}", id);
        return agentRepository.findById(id);
    }

    /**
     * Delete the agent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agent : {}", id);
        agentRepository.deleteById(id);
    }
}
