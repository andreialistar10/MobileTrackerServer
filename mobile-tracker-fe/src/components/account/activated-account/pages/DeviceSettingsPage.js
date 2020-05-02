import React from "react";
import PhonelinkSetupOutlinedIcon from "@material-ui/icons/PhonelinkSetupOutlined";
import { makeSharedStyle } from "../../../../style/activated-account/shared";

const DeviceSettingsPage = () => {
  const { behindContent, icon } = makeSharedStyle();
  return (
    <div>
      <h3>DeviceSettingsPage</h3>
      <div className={behindContent}>
        <PhonelinkSetupOutlinedIcon className={icon} />
      </div>
    </div>
  );
};

export default DeviceSettingsPage;
