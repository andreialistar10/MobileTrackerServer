import React, { useEffect, useState } from "react";
import RoomOutlinedIcon from "@material-ui/icons/RoomOutlined";
import { makeSharedStyle } from "../../../../style/activated-account/shared";
import MobileTrackerMap from "../common/map/MobileTrackerMap";
import { makeLocationsStyle } from "../../../../style/activated-account/pages/locations";
import FilterLocationForm from "./locations/FilterLocationsForm";
import LocationsModal from "./locations/LocationsModal";
import { getMidnightDate } from "../../../../utils/timeUtils";
import { getAddressesByLatitudeAndLongitude } from "../../../../api/geocoderApi";
import MobileTrackerModalLoadingIndicator from "../common/modals/MobileTrackerModalLoadingIndicator";
const moment = require("moment");

const popupProperties = [
  {
    propertyName: "deviceCode",
    propertyTitle: "ID:",
  },
  {
    propertyName: "name",
    propertyTitle: "Name:",
  },
  {
    propertyName: "date",
    propertyTitle: "Date:",
  },
];
const LocationsPage = () => {
  const { behindContent, icon } = makeSharedStyle();
  const { wrapper, mapContainer, rightSide } = makeLocationsStyle();
  const [selectedDevice, setSelectedDevice] = useState(null);
  const [locationModalOpen, setLocationModalOpen] = useState(false);
  const [startDate, setStartDate] = useState(getMidnightDate());
  const [endDate, setEndDate] = useState(new Date());
  const [foundLocationsFilter, setFoundLocations] = useState([]);
  const [loadingFilter, setLoadingFilter] = useState(false);
  const foundLocations = [
    {
      latitude: 46.778888,
      longitude: 23.637285,
      date: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      locationCoordinates: "46.778888, 23.637285",
      address: "DA",
    },
    {
      latitude: 46.788888,
      longitude: 23.637285,
      date: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      locationCoordinates: "46.778888, 23.637285",
      address: "DA",
    },
    {
      latitude: 46.798888,
      longitude: 23.637285,
      date: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      locationCoordinates: "46.778888, 23.637285",
      address: "DA",
    },
    {
      latitude: 46.808888,
      longitude: 23.637285,
      date: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      locationCoordinates: "46.778888, 23.637285",
      address: "DA",
    },
    {
      latitude: 46.818888,
      longitude: 23.637285,
      date: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      locationCoordinates: "46.778888, 23.637285",
      address: "DA",
    },
    {
      latitude: 46.828888,
      longitude: 23.637285,
      date: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      locationCoordinates: "46.778888, 23.637285",
      address: "DA",
    },
    {
      latitude: 46.838888,
      longitude: 23.637285,
      date: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      locationCoordinates: "46.778888, 23.637285",
      address: "DA",
    },
    {
      latitude: 46.848888,
      longitude: 23.637285,
      date: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      locationCoordinates: "46.778888, 23.637285",
      address: "DA",
    },
  ];

  useEffect(() => {
    if (loadingFilter === true) {
      getAddressesByLatitudeAndLongitude(foundLocations)
        .then((addresses) => {
          addresses.forEach(
            (address, i) => (foundLocations[i].address = address.display_name)
          );
          setFoundLocations([...foundLocations]);
        })
        .then(() => {
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
  const devices = [
    {
      id: "MOTR_112312313_1312312313211",
      name: "Andrei",
      date: new Date().toDateString(),
    },
    {
      id: "MOTR_23123123123_31231231232",
      name: "Andreea",
      date: new Date(23).toDateString(),
    },
    {
      id: "MOTR_342342342_4234234234234",
      name: "Cosmin",
      date: new Date(23).toDateString(),
    },
    {
      id: "MOTR_431231231_3123123123131",
      name: "Mihai",
      date: new Date(23).toDateString(),
    },
    {
      id: "MOTR_546231231_3123123123131",
      name: "Radu",
      date: new Date(23).toDateString(),
    },
    {
      id: "MOTR_665231231_3123123123131",
      name: "Marius",
      date: new Date(23).toDateString(),
    },
  ];
  const markers = [
    {
      deviceCode: "MOTR_112312313_1312312313211",
      name: "Andrei",
      date: moment.unix(1586567898).format("DD-MM-YYYY HH:mm"),
      latitude: 46.767538,
      longitude: 23.637285,
    },
    {
      deviceCode: "MOTR_665231231_3123123123131",
      name: "Marius",
      date: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      latitude: 46.777538,
      longitude: 23.637285,
      circleColor: "blue",
    },
  ];
  return (
    <div className={wrapper}>
      <div className={behindContent}>
        <RoomOutlinedIcon className={icon} />
      </div>
      <div className={mapContainer}>
        <MobileTrackerMap
          markers={markers}
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
          locations={foundLocationsFilter}
          open={locationModalOpen}
          deviceName={selectedDevice.name}
          onClose={onModalClose}
        />
      )}
      <MobileTrackerModalLoadingIndicator loading={loadingFilter} />
    </div>
  );
};

export default LocationsPage;
