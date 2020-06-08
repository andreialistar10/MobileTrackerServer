import React, { useState } from "react";
import RoomOutlinedIcon from "@material-ui/icons/RoomOutlined";
import { makeSharedStyle } from "../../../../style/activated-account/shared";
import MobileTrackerMap from "../common/map/MobileTrackerMap";
import { makeLocationsStyle } from "../../../../style/activated-account/pages/locations";
import MobileTrackerTableContainer from "../common/containers/MobileTrackerTableContainer";
import SelectDeviceDialog from "./devices/SelectDeviceDialog";
import MobileTrackerLabel from "../common/form/MobileTrackerLabel";
import MobileTrackerRow from "../common/containers/MobileTrackerRow";
import MobileTrackerButton from "../common/form/MobileTrackerButton";

const LocationsPage = () => {
  const { behindContent, icon, inlineFormButton } = makeSharedStyle();
  const {
    wrapper,
    mapContainer,
    rightSide,
    formContainer,
    formTitle,
    formTable,
    submitButton,
  } = makeLocationsStyle();
  const [selectedDevice, setSelectedDevice] = useState(null);
  const handleOnFindLocationSubmit = (event) => {
    console.log("DA");
    event.preventDefault();
  };
  const handleOnDeviceSelected = (device) => {
    console.log(device);
    setSelectedDevice(device);
  };
  const devices = [
    {
      id: "MOTR_1",
      name: "Baran1",
      date: new Date().toDateString(),
    },
    {
      id: "MOTR_2",
      name: "Baran2",
      date: new Date(23).toDateString(),
    },
    {
      id: "MOTR_3",
      name: "Baran3",
      date: new Date(23).toDateString(),
    },
    {
      id: "MOTR_4",
      name: "Baran4",
      date: new Date(23).toDateString(),
    },
    {
      id: "MOTR_5",
      name: "Baran5",
      date: new Date(23).toDateString(),
    },
    {
      id: "MOTR_6",
      name: "Baran6",
      date: new Date(23).toDateString(),
    },
  ];
  const markers = [
    {
      deviceCode: "MOTR_00000000_122112121-12312312",
      name: "Andrei",
      timestamp: 1234567898,
      latitude: 46.767538,
      longitude: 23.637285,
    },
    {
      deviceCode: "MOTR_122112121-1231231200000000",
      name: "Andrei 2",
      timestamp: new Date().getTime(),
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
        <MobileTrackerMap markers={markers} />
      </div>
      <div className={rightSide}>
        <h2 className={formTitle}>Find device locations</h2>
        <form onSubmit={handleOnFindLocationSubmit} className={formContainer}>
          <MobileTrackerTableContainer className={formTable}>
            <SelectDeviceDialog
              onSelect={handleOnDeviceSelected}
              devices={devices}
            />
            <MobileTrackerRow>
              <MobileTrackerLabel textLabel="Start date:" />
            </MobileTrackerRow>
            <MobileTrackerRow>
              <MobileTrackerLabel textLabel="End date:" />
            </MobileTrackerRow>
          </MobileTrackerTableContainer>
          <MobileTrackerButton
            textOnDisable="Find locations"
            text="Find locations"
            disabled={false}
            className={`${inlineFormButton} ${submitButton}`}
          />
        </form>
      </div>
    </div>
  );
};

export default LocationsPage;
