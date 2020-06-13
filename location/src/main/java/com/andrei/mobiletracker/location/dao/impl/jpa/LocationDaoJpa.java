package com.andrei.mobiletracker.location.dao.impl.jpa;

import com.andrei.mobiletracker.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface LocationDaoJpa extends JpaRepository<Location, Long> {

    @Query(value = "SELECT * FROM LOCATIONS as L INNER JOIN " +
            "(SELECT MAX(lMaxD.SENT_AT) AS max_timestamp, lMaxD.DEVICE_CODE " +
            "FROM LOCATIONS as lMaxD " +
            "WHERE lMaxD.DEVICE_CODE in :list " +
            "GROUP BY lMaxD.DEVICE_CODE) as MAX_TIMESTAMP_LOCATIONS ON " +
            "MAX_TIMESTAMP_LOCATIONS.DEVICE_CODE = L.DEVICE_CODE AND MAX_TIMESTAMP_LOCATIONS.max_timestamp = L.SENT_AT",
            nativeQuery = true)
    List<Location> findLatestLocations(@Param("list") List<String> deviceIdList);

    List<Location> findAllByDateBetweenAndDeviceCodeOrderByDateDesc(Long startDate, Long endDate, String deviceCode);

}
