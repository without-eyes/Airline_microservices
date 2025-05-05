package com.airline_microservices.crew;

import com.airline_microservices.crew.CrewMember;
import com.airline_microservices.crew.CrewMemberRepository;
import com.airline_microservices.crew.CrewMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/dispatcher/crew")
public class CrewController {
    @Autowired
    private RestTemplateConfig restTemplate;
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

//    @PostMapping("/{flightId}")
//    public ResponseEntity<CrewMember> addCrewMember(@PathVariable Long flightId, @RequestBody CrewMember crewMember) {
//        Flight flight = restTemplate.getForObject("http://localhost:8081/admin/flights/" + flightId, Flight.class);;
//        crewMember.setFlight(flight);
//        return ResponseEntity.status(201).body(crewMemberService.saveCrewMember(crewMember));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CrewMember> removeCrewMember(@PathVariable Long id) {
        crewMemberService.deleteCrewMember(id);
        return ResponseEntity.status(204).body(null);
    }
}