package com.bachpan.school.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Organization entity.
 */
public class OrganizationDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private Long parentOrgId;

    private String parentOrgName;

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

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long organizationId) {
        this.parentOrgId = organizationId;
    }

    public String getParentOrgName() {
        return parentOrgName;
    }

    public void setParentOrgName(String organizationName) {
        this.parentOrgName = organizationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganizationDTO organizationDTO = (OrganizationDTO) o;

        if ( ! Objects.equals(id, organizationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrganizationDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", type='" + type + "'" +
                '}';
    }
}
