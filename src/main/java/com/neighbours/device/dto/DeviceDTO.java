package com.neighbours.device.dto;

import lombok.Data;
import java.util.List;

@Data
public class DeviceDTO {
    private String mac;
    private String type;
    private List<IPInfoDTO> ips_int;
    private List<NeighborInfoDTO> neighbors_int;
}

