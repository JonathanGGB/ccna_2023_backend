package com.neighbours.device.service;

import com.neighbours.device.entity.Neighbor;
import com.neighbours.device.repository.NeighborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeighborService {
    @Autowired
    private NeighborRepository neighborRepository;
    public void saveNeighbor(Neighbor neighbor) {
        neighborRepository.save(neighbor);
    }
}
