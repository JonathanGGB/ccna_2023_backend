package com.neighbours.device.service;

import com.neighbours.device.entity.DeviceType;
import com.neighbours.device.repository.DeviceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceTypeService {
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    public DeviceType getDeviceTypeByName(String name) {
        Optional<DeviceType> deviceTypeOptional = deviceTypeRepository.findByName(name);

        if (deviceTypeOptional.isPresent()) {
            return deviceTypeOptional.get();
        } else {
            DeviceType newDeviceType = new DeviceType();
            newDeviceType.setName(name);
            deviceTypeRepository.save(newDeviceType);
            return newDeviceType;
        }
    }
}
