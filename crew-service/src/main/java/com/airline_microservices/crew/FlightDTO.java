package com.airline_microservices.crew;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlightDTO {
    private Long id;
    private String destination;
    private String departureTime;
}