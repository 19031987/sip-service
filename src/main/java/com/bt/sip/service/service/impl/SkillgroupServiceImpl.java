package com.bt.sip.service.service.impl;

import com.bt.sip.service.service.SkillgroupService;
import com.bt.sip.service.domain.Skillgroup;
import com.bt.sip.service.repository.SkillgroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Skillgroup.
 */
@Service
@Transactional
public class SkillgroupServiceImpl implements SkillgroupService {

    private final Logger log = LoggerFactory.getLogger(SkillgroupServiceImpl.class);

    private final SkillgroupRepository skillgroupRepository;

    public SkillgroupServiceImpl(SkillgroupRepository skillgroupRepository) {
        this.skillgroupRepository = skillgroupRepository;
    }

    /**
     * Save a skillgroup.
     *
     * @param skillgroup the entity to save
     * @return the persisted entity
     */
    @Override
    public Skillgroup save(Skillgroup skillgroup) {
        log.debug("Request to save Skillgroup : {}", skillgroup);
        return skillgroupRepository.save(skillgroup);
    }

    /**
     * Get all the skillgroups.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Skillgroup> findAll() {
        log.debug("Request to get all Skillgroups");
        return skillgroupRepository.findAll();
    }


    /**
     * Get one skillgroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Skillgroup> findOne(Long id) {
        log.debug("Request to get Skillgroup : {}", id);
        return skillgroupRepository.findById(id);
    }

    /**
     * Delete the skillgroup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skillgroup : {}", id);
        skillgroupRepository.deleteById(id);
    }
}
