package com.airline_microservices.service;

import com.airline_microservices.model.CrewMember;
import com.airline_microservices.repository.CrewMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewMemberService {
    private final CrewMemberRepository crewMemberRepository;

    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public List<CrewMember> getCrewByFlight(Long flightId) {
        return crewMemberRepository.findByFlightId(flightId);
    }

    public CrewMember saveCrewMember(CrewMember crewMember) {
        return crewMemberRepository.save(crewMember);
    }

    public void deleteCrewMember(Long id) {
        crewMemberRepository.deleteById(id);
    }
}