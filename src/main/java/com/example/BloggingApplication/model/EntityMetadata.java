package com.example.BloggingApplication.model;

import jakarta.persistence.Column;

import java.util.Date;

public class EntityMetadata {
    private Date createdAt;
    private Date updatedAt;

    public EntityMetadata() {
    }

    public EntityMetadata(Date createdAt, Date updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "EntityMetadata{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
