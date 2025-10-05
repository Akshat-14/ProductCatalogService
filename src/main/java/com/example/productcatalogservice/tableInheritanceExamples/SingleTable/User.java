package com.example.productcatalogservice.tableInheritanceExamples.SingleTable;

import jakarta.persistence.*;

@Entity(name="st_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type_",discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    Long id;
    String email;
}
