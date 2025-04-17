package com.airline_microservices.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Flight")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Destination")
    private String destination;

    @Column(name = "DepartureTime")
    private String departureTime;
}