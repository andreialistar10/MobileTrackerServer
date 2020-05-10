import React from "react";
import AddDeviceForm from "./AddDeviceForm";
import MobileTrackerModal from "../../common/MobileTrackerModal";
import { makeDevicesStyle } from "../../../../../style/activated-account/pages/devices";
import PropTypes from "prop-types";

const DeviceModal = ({ isOpen, handleOnClose, handleSubmit, loading }) => {
  const { pairingImage, onPairing, onNonPairing } = makeDevicesStyle();
  const imageUrl = loading
    ? "http://localhost:3002/images/pairing.gif"
    : "http://localhost:3002/images/pairing.jpg";
  const className = loading
    ? `${pairingImage} ${onPairing}`
    : `${pairingImage} ${onNonPairing}`;
  return (
    <MobileTrackerModal
      handleOnClose={handleOnClose}
      isOpen={isOpen}
      title={loading ? "Pairing..." : "Add new device"}
      closeIsDisabled={loading}
    >
      <img src={imageUrl} alt="" className={className} />
      {!loading && (
        <AddDeviceForm handleSubmit={handleSubmit} loading={loading} />
      )}
    </MobileTrackerModal>
  );
};

DeviceModal.propTypes = {
  isOpen: PropTypes.bool.isRequired,
  handleOnClose: PropTypes.func.isRequired,
  loading: PropTypes.bool.isRequired,
  handleSubmit: PropTypes.func.isRequired,
};

export default DeviceModal;
