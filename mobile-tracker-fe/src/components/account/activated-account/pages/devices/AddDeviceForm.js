import React, { useState } from "react";
import MobileTrackerTextField from "../../common/form/MobileTrackerTextField";
import MobileTrackerRow from "../../common/containers/MobileTrackerRow";
import PropTypes from "prop-types";
import MobileTrackerTableContainer from "../../common/containers/MobileTrackerTableContainer";
import { makeDevicesStyle } from "../../../../../style/activated-account/pages/devices";
import MobileTrackerButton from "../../common/form/MobileTrackerButton";
import MobileTrackerLabel from "../../common/form/MobileTrackerLabel";
import MobileTrackerCell from "../../common/containers/MobileTrackerCell";
import Switch from "@material-ui/core/Switch";
import FormControl from "@material-ui/core/FormControl";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import SelectDeviceDialog from "./SelectDeviceDialog";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core";
import {
  COLOR_TITLE_PAGE,
  LABEL_COLOR,
} from "../../../../../style/activated-account/constants";
import MobileTrackerAlertModal from "../../common/modals/MobileTrackerAlertModal";

const AddDeviceForm = ({ loading, handleSubmit, devices }) => {
  const [deviceCode, setDeviceCode] = useState("");
  const [devicePassword, setDevicePassword] = useState("");
  const [replacedDevice, setReplacedDevice] = useState("");
  const [replacedDeviceName, setReplacedDeviceName] = useState("");
  const [replaceDevice, setReplaceDevice] = useState(false);
  const [openAlert, setOpenAlert] = useState(false);

  const dataIsValid = () => {
    if (deviceCode === "" || devicePassword === "") {
      return false;
    }
    return !(replaceDevice === true && replacedDevice === "");
  };

  const {
    deviceFormContainer,
    switchContainer,
    switchWrapper,
    switchLabel,
  } = makeDevicesStyle();
  const onDeviceCodeChange = (event) => {
    setDeviceCode(event.target.value);
  };

  const onDevicePasswordChange = (event) => {
    setDevicePassword(event.target.value);
  };

  const handleOnSubmit = () => {
    handleSubmit({
      id: deviceCode,
      password: devicePassword,
      idAfterPairing: replacedDevice,
    });
  };

  const handleOnAgree = () => {
    setDevicePassword("");
    setOpenAlert(false);
    handleOnSubmit();
  };

  const handleOnDisagree = () => {
    setOpenAlert(false);
  };

  const handleAddDeviceOnSubmit = (event) => {
    event.preventDefault();
    if (dataIsValid()) {
      if (replaceDevice) {
        setOpenAlert(true);
      } else {
        handleOnSubmit();
      }
    }
  };

  const onReplacedDeviceChange = (device) => {
    setReplacedDevice(device.id);
    setReplacedDeviceName(device.name);
  };

  const onChangeReplaceDevice = () => {
    setReplaceDevice(!replaceDevice);
    setReplacedDevice("");
  };

  const theme = createMuiTheme({
    palette: {
      primary: { main: COLOR_TITLE_PAGE },
    },
  });

  const getMessage = () => {
    return (
      <p
        style={{
          textAlign: "justify",
          color: "rgb(121, 110, 110)",
          fontSize: "1.2rem",
        }}
      >
        You have chosen to register the new device by replacing the one with the
        ID: <b>{replacedDevice}</b> and the name: <b>{replacedDeviceName}</b>.
        Are you sure you want to do this? The old device will be disconnected
        and will not be able to be tracked unless you register it again.
      </p>
    );
  };

  return (
    <MuiThemeProvider theme={theme}>
      <form onSubmit={handleAddDeviceOnSubmit} className={deviceFormContainer}>
        <div className={switchContainer}>
          <FormControl component="fieldset" className={switchWrapper}>
            <FormControlLabel
              className={switchLabel}
              control={
                <Switch
                  checked={replaceDevice}
                  onChange={onChangeReplaceDevice}
                  color="primary"
                  name="replaceDevice"
                />
              }
              label={
                <span className={switchLabel}>
                  Replace with an existing device
                </span>
              }
            />
          </FormControl>
        </div>
        <MobileTrackerTableContainer>
          <MobileTrackerRow>
            <MobileTrackerTextField
              textLabel="Device code:"
              onValueChange={onDeviceCodeChange}
              value={deviceCode}
              readOnly={loading}
            />
          </MobileTrackerRow>
          <MobileTrackerRow>
            <MobileTrackerTextField
              readOnly={loading}
              textLabel="Password:"
              onValueChange={onDevicePasswordChange}
              value={devicePassword}
            />
          </MobileTrackerRow>
          <MobileTrackerRow show={replaceDevice}>
            <MobileTrackerLabel textLabel="Replaced device:" />
            <MobileTrackerCell>
              <SelectDeviceDialog
                devices={devices}
                onSelect={onReplacedDeviceChange}
              />
            </MobileTrackerCell>
          </MobileTrackerRow>
        </MobileTrackerTableContainer>
        <MobileTrackerButton
          textOnDisable="Add device"
          text="Add device"
          disabled={!dataIsValid()}
        />
      </form>
      <MobileTrackerAlertModal
        handleOnAgree={handleOnAgree}
        open={openAlert}
        message={getMessage()}
        handleOnDisagree={handleOnDisagree}
        title="Do you want to replace selected device?"
        agreeButtonText={"YES"}
        disagreeButtonText={"NO"}
      />
    </MuiThemeProvider>
  );
};

AddDeviceForm.propTypes = {
  loading: PropTypes.bool.isRequired,
  handleSubmit: PropTypes.func.isRequired,
  devices: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
      date: PropTypes.string.isRequired,
    })
  ).isRequired,
};

export default AddDeviceForm;
