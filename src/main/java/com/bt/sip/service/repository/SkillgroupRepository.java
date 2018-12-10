package com.bt.sip.service.repository;

import com.bt.sip.service.domain.Skillgroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Skillgroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillgroupRepository extends JpaRepository<Skillgroup, Long> {

}
