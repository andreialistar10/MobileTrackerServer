import React from "react";
import PhoneAndroidOutlinedIcon from "@material-ui/icons/PhoneAndroidOutlined";
import { makeSharedStyle } from "../../../../style/activated-account/shared";

const DevicesPage = () => {
  const { behindContent, icon } = makeSharedStyle();
  return (
    <div>
      <h3>DevicesPage</h3>
      <div className={behindContent}>
        <PhoneAndroidOutlinedIcon className={icon} />
      </div>
    </div>
  );
};

export default DevicesPage;
