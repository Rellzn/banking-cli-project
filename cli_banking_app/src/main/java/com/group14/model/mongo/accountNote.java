package com.group14.model.mongo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class accountNote {
@BsonId
    private ObjectId id;

    private Long employeeId;   
    private Long accountId;    
    private noteCategory category;
    private String content;      
    private Instant timeCreated;
    private List<String> tags = new ArrayList<>();


    //constructors
    public accountNote() {
    }

    public accountNote(Long employeeId, Long accountId, noteCategory category,
                       String content, List<String> tags) {
        this.employeeId = employeeId;
        this.accountId = accountId;
        this.category = category;
        this.content = content;
        this.timeCreated = Instant.now();
        this.tags = (tags != null) ? new ArrayList<>(tags) : new ArrayList<>();
    }

    //getters + setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public noteCategory getCategory() {
        return category;
    }

    public void setCategory(noteCategory category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof accountNote other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "accountNote{id=" + id
                + ", employeeId=" + employeeId
                + ", accountId=" + accountId
                + ", category='" + category + '\''
                + ", content=" + (content == null ? "null" : content.length() + " chars")
                + ", timeCreated=" + timeCreated
                + ", tags=" + tags + '}';
    }
}
