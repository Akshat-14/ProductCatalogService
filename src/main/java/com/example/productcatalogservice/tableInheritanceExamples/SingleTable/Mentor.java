package com.example.productcatalogservice.tableInheritanceExamples.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name="st_mentor")
@DiscriminatorValue(value="MENTOR")
public class Mentor extends User {
    double ratings;
}
