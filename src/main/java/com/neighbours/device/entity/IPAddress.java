package com.neighbours.device.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class IPAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String ipAddress;

    @Column
    private String ipInterface;
}
