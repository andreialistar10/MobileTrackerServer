package com.andrei.mobiletracker.device.dao.device.impl.jpa;

import com.andrei.mobiletracker.device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface DeviceDaoJpa extends JpaRepository<Device, String> {

    Device findByCodeAndOwnerUsernameAndDeleted(String code, String ownerUsername, Boolean deleted);

    List<Device> findAllByOwnerUsernameAndDeleted(String username, Boolean deleted);

    List<Device> findAllByOwnerUsername(String username);

    Device findByCodeAndOwnerUsername(String id, String ownerUsername);
}
