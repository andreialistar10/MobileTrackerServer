import React from "react";
import RoomOutlinedIcon from "@material-ui/icons/RoomOutlined";
import { makeSharedStyle } from "../../../../style/activated-account/shared";

const LocationsPage = () => {
  const { behindContent, icon } = makeSharedStyle();
  return (
    <div>
      <h3>LocationsPage</h3>
      <div className={behindContent}>
        <RoomOutlinedIcon className={icon} />
      </div>
    </div>
  );
};

export default LocationsPage;
