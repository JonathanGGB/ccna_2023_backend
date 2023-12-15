package com.neighbours.device.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "neighbors")
@Data
public class Neighbor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne
    @JoinColumn(name = "neighbor_id")
    private Device neighbor;

    @Column
    private String interfaceName;
}
