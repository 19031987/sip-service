package com.bt.sip.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Queue.
 */
@Entity
@Table(name = "queue")
public class Queue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "queuename")
    private String queuename;

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

    public String getQueuename() {
        return queuename;
    }

    public Queue queuename(String queuename) {
        this.queuename = queuename;
        return this;
    }

    public void setQueuename(String queuename) {
        this.queuename = queuename;
    }

    public Skillgroup getSkillgroup() {
        return skillgroup;
    }

    public Queue skillgroup(Skillgroup skillgroup) {
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
        Queue queue = (Queue) o;
        if (queue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), queue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Queue{" +
            "id=" + getId() +
            ", queuename='" + getQueuename() + "'" +
            "}";
    }
}
