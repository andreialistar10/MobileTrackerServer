import React, { useEffect } from "react";
import PhoneAndroidOutlinedIcon from "@material-ui/icons/PhoneAndroidOutlined";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import { makeDevicesStyle } from "../../../../../style/activated-account/pages/devices";
import DevicesTable from "./DevicesTable";
import DeviceModal from "./DevicesModal";
import SockJsClient from "react-stomp";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import MobileTrackerErrorModal from "../../common/modals/MobileTrackerErrorModal";
import MobileTrackerInfoModal from "../../common/modals/MobileTrackerInfoModal";
import {
  addDevice,
  getAllUserDevices,
  updateDevice,
} from "../../../../../redux/actions/deviceActions";
import { toast } from "react-toastify";
import MobileTrackerModalLoadingIndicator from "../../common/modals/MobileTrackerModalLoadingIndicator";

const initialCredentials = {
  id: "",
  password: "",
};

const DevicesPage = ({
  username,
  getAllDevices,
  devices,
  addDevice,
  updateDevice,
}) => {
  const { behindContent, icon } = makeSharedStyle();
  const { root } = makeDevicesStyle();
  const [isOpen, setIsOpen] = React.useState(false);
  const [pairingLoading, setPairingLoading] = React.useState(false);
  const [loadingPage, setLoadingPage] = React.useState(true);
  const [credentials, setCredentials] = React.useState({
    ...initialCredentials,
  });
  const [errorMessage, setErrorMessage] = React.useState("");
  const [
    successfullyPairingMessage,
    setSuccessfullyPairingMessage,
  ] = React.useState("");
  const sockJsClientRef = React.createRef(null);

  const handleAddDevice = () => {
    setIsOpen(true);
  };

  const handleClose = () => {
    if (!pairingLoading) {
      setIsOpen(false);
    }
  };

  const handlePairing = (credentials) => {
    setCredentials(credentials);
    setPairingLoading(true);
  };

  const sendMessage = () => {
    const { current } = sockJsClientRef;
    if (current != null) {
      current.sendMessage(
        `/device-connectivity/pairing/${credentials.id}/user/${username}`,
        JSON.stringify(credentials)
      );
    }
  };

  const handleOnMessageArrive = (message, topic) => {
    if (topic.startsWith("/errors")) {
      handleOnErrorMessageArrive(message);
    } else {
      handleOnSuccessMessageArrive(message);
    }
  };

  const handleOnSuccessMessageArrive = (message) => {
    const {
      deviceCode,
      state,
      deviceName: name,
      registeredOn: date,
      deviceCodeAfterPairing: id,
    } = message;
    if (state === "PAIRED") {
      setSuccessfullyPairingMessage(
        `Now you can track the locations of ${name}'s smartphone.`
      );

      const newDevice = { id, name, date: new Date(date).toDateString() };
      if (id === deviceCode) {
        addDevice(newDevice);
      } else {
        updateDevice(newDevice);
      }
      setPairingLoading(false);
      setIsOpen(false);
    }
  };

  const getErrorMessage = (message) => {
    const { state } = message;
    if (state === "UNPAIRED") {
      return "The pairing mode has been turned off or your device was disconnected. Make sure that your smartphone is connected to the internet and pairing mode is turned on.";
    }
    return "Something went wrong. Please try again later!";
  };

  const handleOnErrorMessageArrive = (message) => {
    const messageErrorText = message.payload
      ? message.payload
      : getErrorMessage(message);
    setPairingLoading(false);
    setErrorMessage(messageErrorText);
  };

  useEffect(() => {
    getAllDevices()
      .catch((error) => {
        toast.error(error.message);
      })
      .finally(() => setLoadingPage(false));
  }, []);

  return loadingPage ? (
    <MobileTrackerModalLoadingIndicator loading={loadingPage} />
  ) : (
    <div className={root}>
      <div className={behindContent}>
        <PhoneAndroidOutlinedIcon className={icon} />
      </div>
      <DevicesTable handleOnAdd={handleAddDevice} devices={devices} />
      <MobileTrackerInfoModal
        isOpen={successfullyPairingMessage !== ""}
        handleOnClose={() => setSuccessfullyPairingMessage("")}
        title={"Successfully Paired"}
        message={successfullyPairingMessage}
      />
      {pairingLoading && (
        <SockJsClient
          url="http://localhost:8082/mobile-tracker/device-connectivity"
          topics={[
            `/users/${username}/pairing-device/${credentials.id}`,
            `/errors/pairing-device/${credentials.id}/user/${username}`,
          ]}
          onMessage={handleOnMessageArrive}
          onConnect={() => {
            console.log("CONNECTED");
            sendMessage();
          }}
          ref={(client) => {
            sockJsClientRef.current = client;
          }}
        />
      )}
      <DeviceModal
        isOpen={isOpen}
        handleOnClose={handleClose}
        handleSubmit={handlePairing}
        loading={pairingLoading}
        devices={devices}
      />
      <MobileTrackerErrorModal
        isOpen={errorMessage !== ""}
        title="Pairing Error"
        handleOnClose={() => setErrorMessage("")}
        message={errorMessage}
      />
    </div>
  );
};

DevicesPage.propTypes = {
  username: PropTypes.string.isRequired,
  getAllDevices: PropTypes.func.isRequired,
  addDevice: PropTypes.func.isRequired,
  updateDevice: PropTypes.func.isRequired,
  devices: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
      date: PropTypes.string.isRequired,
    })
  ).isRequired,
};

const mapDispatchToProps = {
  getAllDevices: getAllUserDevices,
  addDevice,
  updateDevice,
};

const mapStateToProps = ({ authorization, devices }) => {
  const newDevices = devices.devicesList.map((device) => {
    const newDevice = { ...device };
    newDevice.date = new Date(device.date).toDateString();
    return newDevice;
  });
  return {
    username: authorization.username,
    devices: newDevices,
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DevicesPage);
