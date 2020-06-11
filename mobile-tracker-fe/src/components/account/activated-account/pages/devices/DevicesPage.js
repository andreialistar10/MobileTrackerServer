import React from "react";
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

const initialCredentials = {
  id: "",
  password: "",
};

const DevicesPage = ({ username }) => {
  const { behindContent, icon } = makeSharedStyle();
  const { root } = makeDevicesStyle();
  const [isOpen, setIsOpen] = React.useState(false);
  const [pairingLoading, setPairingLoading] = React.useState(false);
  const [credentials, setCredentials] = React.useState({
    ...initialCredentials,
  });
  const [errorMessage, setErrorMessage] = React.useState("");
  const [
    successfullyPairingMessage,
    setSuccessfullyPairingMessage,
  ] = React.useState("");
  const [devices, setDevices] = React.useState([
    {
      id: "MOTR_112312313_1312312313211",
      name: "Andrei",
      date: new Date().toDateString(),
    },
    {
      id: "MOTR_23123123123_31231231232",
      name: "Andreea",
      date: new Date(1584567891111).toDateString(),
    },
    {
      id: "MOTR_342342342_4234234234234",
      name: "Cosmin",
      date: new Date(1584569891111).toDateString(),
    },
    {
      id: "MOTR_431231231_3123123123131",
      name: "Mihai",
      date: new Date(1584667891111).toDateString(),
    },
    {
      id: "MOTR_546231231_3123123123131",
      name: "Radu",
      date: new Date(1584577891111).toDateString(),
    },
    {
      id: "MOTR_665231231_3123123123131",
      name: "Marius",
      date: new Date(1584567891111).toDateString(),
    },
    {
      id: "MOTR_665231231_3123123123131",
      name: "Marius",
      date: new Date(1584567891111).toDateString(),
    },
  ]);
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
    console.log(message);
    if (topic.startsWith("/errors")) {
      handleOnErrorMessageArrive(message);
    } else {
      handleOnSuccessMessageArrive(message);
    }
  };

  const handleOnSuccessMessageArrive = (message) => {
    console.log(message);
    const {
      deviceCode: id,
      state,
      deviceName: name,
      registeredOn: date,
    } = message;
    if (state === "PAIRED") {
      setSuccessfullyPairingMessage(
        "Now you can track the locations of Test's smartphone."
      );
      setDevices((prevState) => {
        const newDevice = { id, name, date };
        return Array.isArray(devices) ? prevState.concat(newDevice) : newDevice;
      });
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

  return (
    <div className={root}>
      <div className={behindContent}>
        <PhoneAndroidOutlinedIcon className={icon} />
      </div>
      <DevicesTable handleOnAdd={handleAddDevice} devices={devices} />
      <MobileTrackerInfoModal
        isOpen={successfullyPairingMessage !== ""}
        handleOnClose={() => setSuccessfullyPairingMessage("")}
        title={"Successfully Pairing"}
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
};

const mapStateToProps = ({ authorization }) => {
  return {
    username: authorization.username,
  };
};

export default connect(mapStateToProps)(DevicesPage);
