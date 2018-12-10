package com.bt.sip.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bt.sip.service.domain.Skillgroup;
import com.bt.sip.service.service.SkillgroupService;
import com.bt.sip.service.web.rest.errors.BadRequestAlertException;
import com.bt.sip.service.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Skillgroup.
 */
@RestController
@RequestMapping("/api")
public class SkillgroupResource {

    private final Logger log = LoggerFactory.getLogger(SkillgroupResource.class);

    private static final String ENTITY_NAME = "sipserviceSkillgroup";

    private final SkillgroupService skillgroupService;

    public SkillgroupResource(SkillgroupService skillgroupService) {
        this.skillgroupService = skillgroupService;
    }

    /**
     * POST  /skillgroups : Create a new skillgroup.
     *
     * @param skillgroup the skillgroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillgroup, or with status 400 (Bad Request) if the skillgroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skillgroups")
    @Timed
    public ResponseEntity<Skillgroup> createSkillgroup(@RequestBody Skillgroup skillgroup) throws URISyntaxException {
        log.debug("REST request to save Skillgroup : {}", skillgroup);
        if (skillgroup.getId() != null) {
            throw new BadRequestAlertException("A new skillgroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Skillgroup result = skillgroupService.save(skillgroup);
        return ResponseEntity.created(new URI("/api/skillgroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skillgroups : Updates an existing skillgroup.
     *
     * @param skillgroup the skillgroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillgroup,
     * or with status 400 (Bad Request) if the skillgroup is not valid,
     * or with status 500 (Internal Server Error) if the skillgroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skillgroups")
    @Timed
    public ResponseEntity<Skillgroup> updateSkillgroup(@RequestBody Skillgroup skillgroup) throws URISyntaxException {
        log.debug("REST request to update Skillgroup : {}", skillgroup);
        if (skillgroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Skillgroup result = skillgroupService.save(skillgroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillgroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skillgroups : get all the skillgroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skillgroups in body
     */
    @GetMapping("/skillgroups")
    @Timed
    public List<Skillgroup> getAllSkillgroups() {
        log.debug("REST request to get all Skillgroups");
        return skillgroupService.findAll();
    }

    /**
     * GET  /skillgroups/:id : get the "id" skillgroup.
     *
     * @param id the id of the skillgroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillgroup, or with status 404 (Not Found)
     */
    @GetMapping("/skillgroups/{id}")
    @Timed
    public ResponseEntity<Skillgroup> getSkillgroup(@PathVariable Long id) {
        log.debug("REST request to get Skillgroup : {}", id);
        Optional<Skillgroup> skillgroup = skillgroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillgroup);
    }

    /**
     * DELETE  /skillgroups/:id : delete the "id" skillgroup.
     *
     * @param id the id of the skillgroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skillgroups/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkillgroup(@PathVariable Long id) {
        log.debug("REST request to delete Skillgroup : {}", id);
        skillgroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
