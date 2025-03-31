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
    public List<CrewMember> getCrewByFlight(@PathVariable Long flightId) {
        return crewMemberService.getCrewByFlight(flightId);
    }

    @PostMapping("/{flightId}")
    public CrewMember addCrewMember(@PathVariable Long flightId, @RequestBody CrewMember crewMember) {
        // Встановлюємо flightId замість об'єкта Flight
        crewMember.setFlightId(flightId);
        return crewMemberService.saveCrewMember(crewMember);
    }

    @DeleteMapping("/{id}")
    public void removeCrewMember(@PathVariable Long id) {
        crewMemberService.deleteCrewMember(id);
    }
}