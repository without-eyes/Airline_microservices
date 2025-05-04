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
    public ResponseEntity<List<CrewMember>> getCrewByFlight(
            @PathVariable Long flightId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String role) {

        List<CrewMember> crewMemberList = crewMemberService.getCrewByFlightWithFilters(flightId, name, role);
        if (crewMemberList.isEmpty()) {
            return ResponseEntity.status(204).body(null);
        } else {
            return ResponseEntity.ok(crewMemberList);
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