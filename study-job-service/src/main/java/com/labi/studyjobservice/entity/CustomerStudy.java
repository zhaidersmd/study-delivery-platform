package com.labi.studyjobservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_study")
public class CustomerStudy {

    @EmbeddedId
    private CustomerStudyId customerStudyId;

    @Column(nullable = false)
    private boolean enabled;

    protected CustomerStudy() {
    }

    public CustomerStudy(CustomerStudyId customerStudyId, boolean enabled) {

        this.customerStudyId = customerStudyId;
        this.enabled = enabled;
    }

}
