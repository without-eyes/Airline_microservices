package com.airline_microservices.crew;

import com.airline_microservices.crew.CrewMember;
import com.airline_microservices.crew.CrewMemberRepository;
import com.airline_microservices.crew.CrewMemberService;
import com.airline_microservices.crew.FlightDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/dispatcher/crew")
public class CrewController {
    @Autowired
    private RestTemplate restTemplate;
    private CrewMemberService crewMemberService;

    @Autowired
    public CrewController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @GetMapping
    public ResponseEntity<List<CrewMember>> getCrewMembers(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) String role,
                                                           @RequestParam(required = false) boolean isAvailable) {
        List<CrewMember> crewMemberList = crewMemberService.getAllCrewByFlightWithFilters(name, role, isAvailable);
        if (crewMemberList.isEmpty()) {
            return ResponseEntity.status(204).body(null);
        } else {
            return ResponseEntity.ok(crewMemberList);
        }
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

    @PostMapping
    public ResponseEntity<CrewMember> createCrewMember(@RequestBody CrewMember crewMember) {
        crewMember.setFlightId(-1L);
        return ResponseEntity.status(201).body(crewMemberService.saveCrewMember(crewMember));
    }

    @PatchMapping("/{flightId}")
    public ResponseEntity<FlightDTO> addCrewMemberToFlight(@PathVariable Long flightId, @RequestBody Long crewMemberId) {
        FlightDTO flight = restTemplate.getForObject(
                "http://flight-service:8081/admin/flights/" + flightId,
                FlightDTO.class
        );;
        CrewMember crewMember = crewMemberService.getCrewMemberById(crewMemberId);
        crewMember.setFlightId(flight.getId());
        crewMemberService.saveCrewMember(crewMember);
        return ResponseEntity.ok(flight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CrewMember> deleteCrewMember(@PathVariable Long id) {
        crewMemberService.deleteCrewMember(id);
        return ResponseEntity.status(204).body(null);
    }

    @PatchMapping("/{flightId}/{crewMemberId}")
    public ResponseEntity<CrewMember> removeCrewMemberFromFlight(@PathVariable Long flightId, @PathVariable Long crewMemberId) {
        CrewMember crewMember = crewMemberService.getCrewMemberById(crewMemberId);
        crewMember.setFlightId(-1L);
        crewMemberService.saveCrewMember(crewMember);
        return ResponseEntity.ok(crewMember);
    }
}