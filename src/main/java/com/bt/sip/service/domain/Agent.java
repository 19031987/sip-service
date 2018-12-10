package com.bt.sip.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Agent.
 */
@Entity
@Table(name = "agent")
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agentname")
    private String agentname;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Skillgroup skillgroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentname() {
        return agentname;
    }

    public Agent agentname(String agentname) {
        this.agentname = agentname;
        return this;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getStatus() {
        return status;
    }

    public Agent status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Skillgroup getSkillgroup() {
        return skillgroup;
    }

    public Agent skillgroup(Skillgroup skillgroup) {
        this.skillgroup = skillgroup;
        return this;
    }

    public void setSkillgroup(Skillgroup skillgroup) {
        this.skillgroup = skillgroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Agent agent = (Agent) o;
        if (agent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agent{" +
            "id=" + getId() +
            ", agentname='" + getAgentname() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
