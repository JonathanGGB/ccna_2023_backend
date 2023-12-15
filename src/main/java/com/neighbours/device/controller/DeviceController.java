package com.neighbours.device.controller;

import com.google.gson.Gson;
import com.neighbours.device.dto.DeviceDTO;
import com.neighbours.device.dto.IPInfoDTO;
import com.neighbours.device.dto.NeighborInfoDTO;
import com.neighbours.device.entity.Device;
import com.neighbours.device.entity.DeviceType;
import com.neighbours.device.entity.IPAddress;
import com.neighbours.device.entity.Neighbor;
import com.neighbours.device.service.DeviceService;
import com.neighbours.device.service.DeviceTypeService;
import com.neighbours.device.service.NeighborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceTypeService deviceTypeService;
    @Autowired
    private NeighborService neighborService;

    @GetMapping("/")
    public ResponseEntity<List<DeviceDTO>> getAllDevicesJson() {
        try {
            List<Device> devices = deviceService.getAllDevices();
            List<DeviceDTO> deviceDTOList = devices.stream()
                    .map(device -> new Gson().fromJson(device.toJson(), DeviceDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(deviceDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createDevice(@RequestBody DeviceDTO deviceDTO) {
        try {
            Device newDevice = convertDTOToDevice(deviceDTO);
            deviceService.saveDevice(newDevice);
            if (deviceDTO.getNeighbors_int() != null) {
                setDeviceNeighbors(newDevice, deviceDTO.getNeighbors_int());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Device created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    private Device convertDTOToDevice(DeviceDTO deviceDTO) {
        Device newDevice = new Device();
        newDevice.setMac(deviceDTO.getMac());

        DeviceType deviceType = deviceTypeService.getDeviceTypeByName(deviceDTO.getType());
        newDevice.setType(deviceType);

        List<IPAddress> ipAddresses = new ArrayList<>();
        if (deviceDTO.getIps_int() != null) {
            for (IPInfoDTO ipInfoDTO : deviceDTO.getIps_int()) {
                IPAddress ipAddress = new IPAddress();
                ipAddress.setIpAddress(ipInfoDTO.getIp());
                ipAddress.setIpInterface(ipInfoDTO.getInt_());
                ipAddresses.add(ipAddress);
            }
        }

        newDevice.setIpAddresses(ipAddresses);

        return newDevice;
    }

    private void setDeviceNeighbors(Device device, List<NeighborInfoDTO> neighborsDTO){
        for (NeighborInfoDTO neighborInfoDTO : neighborsDTO) {
            Device neighbor = deviceService.getDeviceByMac(neighborInfoDTO.getMac());

            if (neighbor == null) {
                neighbor = new Device();
                neighbor.setMac(neighborInfoDTO.getMac());
                deviceService.saveDevice(neighbor);
            }

            Neighbor newNeighbor = new Neighbor();
            newNeighbor.setDevice(device);
            newNeighbor.setNeighbor(neighbor);
            newNeighbor.setInterfaceName(neighborInfoDTO.getInt_());
            neighborService.saveNeighbor(newNeighbor);
        }
    }
}
