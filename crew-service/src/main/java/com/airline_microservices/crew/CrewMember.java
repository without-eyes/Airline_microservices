package com.airline_microservices.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "CrewMembers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Role")
    private String role;

    @Column(name = "flight_id")
    private Long flightId;
}