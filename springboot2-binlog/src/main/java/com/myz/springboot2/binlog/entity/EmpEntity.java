/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.entity;

import java.time.LocalDateTime;

/**
 * @author maoyz0621 on 2021/8/25
 * @version v1.0
 */
public class EmpEntity {
    private Long id;
    private String name;
    private String email;
    private String createdBy;
    private LocalDateTime createdTime;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedTime;
    private String remarks;
    private Integer enabled;
    private Integer version;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmpEntity{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", createdTime=").append(createdTime);
        sb.append(", lastModifiedBy='").append(lastModifiedBy).append('\'');
        sb.append(", lastModifiedTime=").append(lastModifiedTime);
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}