package com.airline_microservices.service;

import com.airline_microservices.model.CrewMember;
import com.airline_microservices.repository.CrewMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewMemberService {
    private CrewMemberRepository crewMemberRepository;

    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public List<CrewMember> getCrewByFlightWithFilters(Long flightId, String name, String role) {
        return crewMemberRepository.findByFlightId(flightId).stream()
                .filter(cm -> name == null || cm.getName().equalsIgnoreCase(name))
                .filter(cm -> role == null || cm.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    public CrewMember saveCrewMember(CrewMember crewMember) {
        return crewMemberRepository.save(crewMember);
    }

    public void deleteCrewMember(Long id) {
        crewMemberRepository.deleteById(id);
    }
}