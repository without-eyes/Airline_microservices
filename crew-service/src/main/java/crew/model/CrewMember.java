package com.airline_microservices.crew;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Role")
    private String role;

    @Column(name = "flight_id")
    private Long flightId;
}