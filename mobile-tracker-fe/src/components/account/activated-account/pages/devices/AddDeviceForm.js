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
import { COLOR_TITLE_PAGE } from "../../../../../style/activated-account/constants";

const AddDeviceForm = ({ loading, handleSubmit, devices }) => {
  const [deviceCode, setDeviceCode] = useState("");
  const [devicePassword, setDevicePassword] = useState("");
  const [replacedDevice, setReplacedDevice] = useState("");
  const [replaceDevice, setReplaceDevice] = useState(false);

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

  const handleAddDeviceOnSubmit = (event) => {
    event.preventDefault();
    if (dataIsValid()) {
      handleSubmit({
        id: deviceCode,
        password: devicePassword,
        idAfterPairing: replacedDevice,
      });
      setDevicePassword("");
    }
  };

  const onReplacedDeviceChange = (id) => {
    // setReplacedDevice(event.target.value);
    setReplacedDevice(id);
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
