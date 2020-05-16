import React from "react";
import PhoneAndroidOutlinedIcon from "@material-ui/icons/PhoneAndroidOutlined";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import { makeDevicesStyle } from "../../../../../style/activated-account/pages/devices";
import DevicesTable from "./DevicesTable";
import DeviceModal from "./DevicesModal";
import SockJsClient from "react-stomp";
import { connect } from "react-redux";
import PropTypes from "prop-types";

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
  const [devices, setDevices] = React.useState([
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
    console.log(credentials);
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

  return (
    <div className={root}>
      <div className={behindContent}>
        <PhoneAndroidOutlinedIcon className={icon} />
      </div>
      <DevicesTable handleOnAdd={handleAddDevice} devices={devices}/>
      <DeviceModal
        isOpen={isOpen}
        handleOnClose={handleClose}
        handleSubmit={handlePairing}
        loading={pairingLoading}
        devices={devices}
      />
      {pairingLoading && (
        <SockJsClient
          url="http://localhost:8082/mobile-tracker/device-connectivity"
          topics={[
            `/users/${username}`,
            `/errors/pairing/${credentials.id}/user/${username}`,
          ]}
          onMessage={(message) => console.log(message)}
          onConnect={() => {
            console.log("CONNECTED");
            sendMessage();
          }}
          ref={(client) => {
            sockJsClientRef.current = client;
          }}
        />
      )}
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
