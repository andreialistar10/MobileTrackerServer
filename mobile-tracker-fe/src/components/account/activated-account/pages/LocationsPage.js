import React, { useEffect, useState } from "react";
import RoomOutlinedIcon from "@material-ui/icons/RoomOutlined";
import { makeSharedStyle } from "../../../../style/activated-account/shared";
import MobileTrackerMap from "../common/map/MobileTrackerMap";
import { makeLocationsStyle } from "../../../../style/activated-account/pages/locations";
import FilterLocationForm from "./locations/FilterLocationsForm";
import LocationsModal from "./locations/LocationsModal";
import { getMidnightDate } from "../../../../utils/timeUtils";
import MobileTrackerModalLoadingIndicator from "../common/modals/MobileTrackerModalLoadingIndicator";
import {
  getLatestLocations,
  getLocationsByDeviceCodeAndTimeInterval,
} from "../../../../api/locationApi";
import { getAllUserDevices } from "../../../../redux/actions/deviceActions";
import { connect } from "react-redux";
import PropTypes from "prop-types";
const moment = require("moment");

const popupProperties = [
  {
    propertyName: "deviceCode",
    propertyTitle: "ID:",
  },
  {
    propertyName: "deviceName",
    propertyTitle: "Name:",
  },
  {
    propertyName: "date",
    propertyTitle: "Date:",
  },
];
const LocationsPage = ({ getAllDevices, devices }) => {
  const { behindContent, icon } = makeSharedStyle();
  const { wrapper, mapContainer, rightSide } = makeLocationsStyle();
  const [selectedDevice, setSelectedDevice] = useState(null);
  const [locationModalOpen, setLocationModalOpen] = useState(false);
  const [startDate, setStartDate] = useState(getMidnightDate());
  const [endDate, setEndDate] = useState(new Date());
  const [latestLocations, setLatestLocations] = useState([]);
  const [filteredLocations, setFilteredLocations] = useState([]);
  const [loadingFilter, setLoadingFilter] = useState(false);
  const [loadingRequestsOnStart, setLoadingRequestsOnStart] = useState(2);

  useEffect(() => {
    getLatestLocations()
      .then((locations) => {
        const newLatestLocations = locations.map((location) => {
          location.date = moment(location.date).format("DD-MM-YYYY HH:mm:ss");
          return location;
        });
        setLatestLocations(newLatestLocations);
      })
      .catch(() => {})
      .finally(() => {
        setLoadingRequestsOnStart((prevState) => prevState - 1);
      });
    getAllDevices()
      .catch(() => {})
      .finally(() => {
        setLoadingRequestsOnStart((prevState) => prevState - 1);
      });
  }, []);

  useEffect(() => {
    if (loadingFilter) {
      getLocationsByDeviceCodeAndTimeInterval(
        selectedDevice.id,
        startDate.getTime(),
        endDate.getTime()
      )
        .then((foundLocations) => {
          foundLocations.forEach((currentLocation) => {
            currentLocation.locationCoordinates = `${currentLocation.latitude}, ${currentLocation.longitude}`;
            currentLocation.date = moment(currentLocation.date).format(
              "DD-MM-YYYY HH:mm"
            );
          });
          setFilteredLocations([...foundLocations]);
        })
        .finally(() => {
          setLoadingFilter(false);
          setLocationModalOpen(true);
        });
    }
  }, [loadingFilter]);

  const handleOnFindLocationSubmit = (event) => {
    event.preventDefault();
    setLoadingFilter(true);
  };
  const handleOnDeviceSelected = (device) => {
    setSelectedDevice(device);
  };
  const onModalClose = () => {
    setLocationModalOpen(false);
    setEndDate(new Date());
    setStartDate(getMidnightDate());
  };

  return (
    <div className={wrapper}>
      <div className={behindContent}>
        <RoomOutlinedIcon className={icon} />
      </div>
      <div className={mapContainer}>
        <MobileTrackerMap
          markers={latestLocations}
          popupProperties={popupProperties}
          markerIdName={"deviceCode"}
        />
      </div>
      <div className={rightSide}>
        <FilterLocationForm
          devices={devices}
          onDeviceSelected={handleOnDeviceSelected}
          onSubmit={handleOnFindLocationSubmit}
          defaultFields={selectedDevice === null}
          startDate={startDate}
          endDate={endDate}
          onStartDateChange={setStartDate}
          onEndDateChange={setEndDate}
          disableSubmit={selectedDevice === null || loadingFilter}
        />
      </div>
      {selectedDevice !== null && (
        <LocationsModal
          locations={filteredLocations}
          open={locationModalOpen}
          deviceName={selectedDevice.name}
          onClose={onModalClose}
        />
      )}
      <MobileTrackerModalLoadingIndicator
        loading={loadingFilter || loadingRequestsOnStart !== 0}
      />
    </div>
  );
};

LocationsPage.propTypes = {
  getAllDevices: PropTypes.func.isRequired,
  devices: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
    })
  ).isRequired,
};

const mapStateToProps = ({ devices }) => {
  return {
    devices: devices.devicesList,
  };
};

const mapDispatchToProps = {
  getAllDevices: getAllUserDevices,
};

export default connect(mapStateToProps, mapDispatchToProps)(LocationsPage);
