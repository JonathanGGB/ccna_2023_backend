package com.neighbours.device.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
}
