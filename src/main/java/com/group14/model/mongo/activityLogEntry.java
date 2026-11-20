package com.group14.model.mongo;

import java.time.Instant;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class activityLogEntry {

    @BsonId
    private ObjectId id;

    private Long employeeId;
    private logCategory logCategory;
    private Instant timeCreated;

    //constructors
    public activityLogEntry() {}

    public activityLogEntry(long employeeId, logCategory logCategory){
        this.employeeId = employeeId;
        this.logCategory = logCategory;
        this.timeCreated = Instant.now();
    }

    //getters + setters
    public ObjectId getId(){
        return id;
    }

    public void setId(ObjectId id){
        this.id = id;
    }

     public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public logCategory getLogCategory() {
        return logCategory;
    }

    public void setLogCategory(logCategory logCategory) {
        this.logCategory = logCategory;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
    }

    //specific object class methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof activityLogEntry other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ActivityLogEntry{id=" + id
                + ", employeeId=" + employeeId
                + ", logCategory='" + logCategory + '\''
                + ", timeCreated=" + timeCreated + '}';
    }
}
