package com.bt.sip.service.service;

import com.bt.sip.service.domain.Skillgroup;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Skillgroup.
 */
public interface SkillgroupService {

    /**
     * Save a skillgroup.
     *
     * @param skillgroup the entity to save
     * @return the persisted entity
     */
    Skillgroup save(Skillgroup skillgroup);

    /**
     * Get all the skillgroups.
     *
     * @return the list of entities
     */
    List<Skillgroup> findAll();


    /**
     * Get the "id" skillgroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Skillgroup> findOne(Long id);

    /**
     * Delete the "id" skillgroup.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
