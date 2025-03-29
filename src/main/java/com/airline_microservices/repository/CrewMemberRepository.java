package com.airline_microservices.repository;

import com.airline_microservices.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    List<CrewMember> findByFlightId(Long flightId);
}