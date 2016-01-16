package com.bachpan.school.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Organization.
 */
@Entity
@Table(name = "ORGANIZATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    
    @Column(name = "name")
    private String name;


    
    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "parentOrg")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Organization> childOrgs = new HashSet<>();

    @ManyToOne
    private Organization parentOrg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Organization> getChildOrgs() {
        return childOrgs;
    }

    public void setChildOrgs(Set<Organization> organizations) {
        this.childOrgs = organizations;
    }

    public Organization getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(Organization organization) {
        this.parentOrg = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Organization organization = (Organization) o;

        if ( ! Objects.equals(id, organization.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", type='" + type + "'" +
                '}';
    }
}
