package com.neighbours.device.service;

import com.neighbours.device.entity.Device;
import com.neighbours.device.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceByMac(String mac) {
        Optional<Device> deviceOptional = deviceRepository.findByMac(mac);
        return deviceOptional.orElse(null);
    }

    public void saveDevice(Device device) {
        deviceRepository.save(device);
    }
}
