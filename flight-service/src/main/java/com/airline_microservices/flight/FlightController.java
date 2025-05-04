package com.airline_microservices.controller;

import com.airline_microservices.model.Flight;
import com.airline_microservices.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/flights")
public class AdminController {
    private FlightService flightService;

    public AdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String departureTime) {

        List<Flight> filteredFlights = flightService.searchFlights(destination, departureTime);
        if (filteredFlights.isEmpty()) {
            return ResponseEntity.status(204).body(null);
        } else {
            return ResponseEntity.ok(filteredFlights);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight foundFlight = flightService.getFlightById(id);
        if (foundFlight != null) {
            return ResponseEntity.status(200).body(foundFlight);
        } else {
            return ResponseEntity.status(204).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        return ResponseEntity.status(201).body(flightService.saveFlight(flight));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlight(id, flight);
        if (updatedFlight != null) {
            return ResponseEntity.status(200).body(updatedFlight);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.status(204).body(null);
    }
}