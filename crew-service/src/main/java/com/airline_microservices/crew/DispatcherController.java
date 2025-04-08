package com.airline_microservices.controller;

import com.airline_microservices.model.CrewMember;
import com.airline_microservices.service.CrewMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispatcher/crew")
public class DispatcherController {
    private CrewMemberService crewMemberService;

    public DispatcherController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<List<CrewMember>> getCrewByFlight(@PathVariable Long flightId) {
        List<CrewMember> crewMemberList = crewMemberService.getCrewByFlight(flightId);
        if (crewMemberList.isEmpty()) {
            return ResponseEntity.status(204).body(null);
        } else {
            return ResponseEntity.status(200).body(crewMemberList);
        }
    }

    @PostMapping("/{flightId}")
    public ResponseEntity<CrewMember> addCrewMember(@PathVariable Long flightId, @RequestBody CrewMember crewMember) {
        Flight flight = flightService.getFlightById(flightId);
        crewMember.setFlight(flight);
        return ResponseEntity.status(201).body(crewMemberService.saveCrewMember(crewMember));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CrewMember> removeCrewMember(@PathVariable Long id) {
        crewMemberService.deleteCrewMember(id);
        return ResponseEntity.status(204).body(null);
    }
}