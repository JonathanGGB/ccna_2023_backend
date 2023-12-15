package com.neighbours.device.repository;

import com.neighbours.device.entity.Neighbor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborRepository extends JpaRepository<Neighbor, Long> {}
