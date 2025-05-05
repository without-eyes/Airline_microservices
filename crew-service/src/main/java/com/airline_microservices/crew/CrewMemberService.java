package com.airline_microservices.crew;

import com.airline_microservices.crew.CrewMember;
import com.airline_microservices.crew.CrewMemberRepository;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class CrewMemberService {
    private CrewMemberRepository crewMemberRepository;

    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public List<CrewMember> getAllCrewByFlightWithFilters(String name, String role, boolean isAvailable) {
        return crewMemberRepository.findAll().stream()
                .filter(cm -> name == null || cm.getName().equalsIgnoreCase(name))
                .filter(cm -> role == null || cm.getRole().equalsIgnoreCase(role))
                .filter(cm -> !isAvailable || cm.getFlight() == null)
                .collect(Collectors.toList());
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