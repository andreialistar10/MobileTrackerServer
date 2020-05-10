import React from "react";
import PhoneAndroidOutlinedIcon from "@material-ui/icons/PhoneAndroidOutlined";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import { makeDevicesStyle } from "../../../../../style/activated-account/pages/devices";
import DevicesTable from "./DevicesTable";
import DeviceModal from "./DevicesModal";

const DevicesPage = () => {
  const { behindContent, icon } = makeSharedStyle();
  const { root } = makeDevicesStyle();
  const [isOpen, setIsOpen] = React.useState(false);
  const [pairingLoading, setPairingLoading] = React.useState(false);

  const handleAdd = (event) => {
    setIsOpen(true);
  };

  const handleClose = () => {
    if (!pairingLoading) {
      setIsOpen(false);
    }
  };

  const handlePairing = (event) => {
    event.preventDefault();
    console.log("DA");
    setPairingLoading(true);
  };

  return (
    <div className={root}>
      <div className={behindContent}>
        <PhoneAndroidOutlinedIcon className={icon} />
      </div>
      <DevicesTable handleOnAdd={handleAdd} />
      <DeviceModal
        isOpen={isOpen}
        handleOnClose={handleClose}
        handleSubmit={handlePairing}
        loading={pairingLoading}
      />
    </div>
  );
};

export default DevicesPage;
