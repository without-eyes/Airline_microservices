package com.airline_microservices.crew;

import com.airline_microservices.crew.FlightDTO;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Service
public class FlightService {
    private RestTemplate restTemplate;

    public FlightService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FlightDTO> searchFlights(String destination, String departureTime) {
        FlightDTO[] flightArray = restTemplate.getForObject(
                "http://flight-service:8081/admin/flights",
                FlightDTO[].class
        );

        if (flightArray == null) {
            return Collections.emptyList();
        }
        List<FlightDTO> flight = Arrays.asList(flightArray);

        return flight.stream()
                .filter(f -> destination == null || f.getDestination().equalsIgnoreCase(destination))
                .filter(f -> departureTime == null || f.getDepartureTime().equalsIgnoreCase(departureTime))
                .collect(Collectors.toList());
    }
}