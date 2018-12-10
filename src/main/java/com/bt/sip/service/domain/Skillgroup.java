package com.bt.sip.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Skillgroup.
 */
@Entity
@Table(name = "skillgroup")
public class Skillgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "skillgroup")
    private Set<Agent> agents = new HashSet<>();
    @OneToMany(mappedBy = "skillgroup")
    private Set<Queue> queues = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Agent> getAgents() {
        return agents;
    }

    public Skillgroup agents(Set<Agent> agents) {
        this.agents = agents;
        return this;
    }

    public Skillgroup addAgent(Agent agent) {
        this.agents.add(agent);
        agent.setSkillgroup(this);
        return this;
    }

    public Skillgroup removeAgent(Agent agent) {
        this.agents.remove(agent);
        agent.setSkillgroup(null);
        return this;
    }

    public void setAgents(Set<Agent> agents) {
        this.agents = agents;
    }

    public Set<Queue> getQueues() {
        return queues;
    }

    public Skillgroup queues(Set<Queue> queues) {
        this.queues = queues;
        return this;
    }

    public Skillgroup addQueue(Queue queue) {
        this.queues.add(queue);
        queue.setSkillgroup(this);
        return this;
    }

    public Skillgroup removeQueue(Queue queue) {
        this.queues.remove(queue);
        queue.setSkillgroup(null);
        return this;
    }

    public void setQueues(Set<Queue> queues) {
        this.queues = queues;
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
        Skillgroup skillgroup = (Skillgroup) o;
        if (skillgroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillgroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Skillgroup{" +
            "id=" + getId() +
            "}";
    }
}
