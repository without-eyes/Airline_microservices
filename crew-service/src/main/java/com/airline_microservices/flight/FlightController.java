package com.airline_microservices.crew;

import com.airline_microservices.crew.FlightService;
import com.airline_microservices.crew.FlightDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/dispatcher/flights")
public class FlightController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FlightService flightService;

    public FlightController() {}

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String departureTime) {
        List<FlightDTO> filteredFlights = flightService.searchFlights(destination, departureTime);
        if (filteredFlights.isEmpty()) {
            return ResponseEntity.status(204).body(null);
        } else {
            return ResponseEntity.ok(filteredFlights);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        FlightDTO foundFlight = restTemplate.getForObject(
                "http://flight-service:8081/admin/flights/" + id,
                FlightDTO.class
        );
        if (foundFlight != null) {
            return ResponseEntity.status(200).body(foundFlight);
        } else {
            return ResponseEntity.status(204).body(null);
        }
    }
}