package com.neighbours.device.entity;

import com.google.gson.Gson;
import com.neighbours.device.dto.DeviceDTO;
import com.neighbours.device.dto.IPInfoDTO;
import com.neighbours.device.dto.NeighborInfoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotBlank(message = "The mac address cannot be blank")
    private String mac;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "device_type_id")
    private DeviceType type;

    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private List<IPAddress> ipAddresses;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "neighbors",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "neighbor_id")
    )
    private List<Neighbor> neighbors;

    public String toJson() {
        Gson gson = new Gson();

        List<IPInfoDTO> ipsInfo = new ArrayList<>();
        if (this.ipAddresses != null) {
            for (IPAddress ipAddress : this.ipAddresses) {
                IPInfoDTO ipInfoDTO = new IPInfoDTO();
                ipInfoDTO.setIp(ipAddress.getIpAddress());
                ipInfoDTO.setInt_(ipAddress.getIpInterface());
                ipsInfo.add(ipInfoDTO);
            }
        }

        DeviceType deviceType = this.type;

        List<NeighborInfoDTO> neighborsInfo = new ArrayList<>();
        System.out.println(this.neighbors.size());
        for (Neighbor neighbor : this.neighbors) {
            System.out.println(neighbor.getInterfaceName());
            NeighborInfoDTO neighborInfoDTO = new NeighborInfoDTO();
            neighborInfoDTO.setMac(neighbor.getNeighbor().getMac());
            neighborInfoDTO.setInt_(neighbor.getInterfaceName());
            neighborsInfo.add(neighborInfoDTO);
        }

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setMac(this.mac);
        deviceDTO.setType(deviceType.getName());
        deviceDTO.setIps_int(ipsInfo);
        deviceDTO.setNeighbors_int(neighborsInfo);

        return gson.toJson(deviceDTO);
    }
}
