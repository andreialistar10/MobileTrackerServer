import React, { useState } from "react";
import MobileTrackerTextField from "../../common/MobileTrackerTextField";
import MobileTrackerRow from "../../common/MobileTrackerRow";
import PropTypes from "prop-types";
import MobileTrackerTableContainer from "../../common/MobileTrackerTableContainer";
import { makeDevicesStyle } from "../../../../../style/activated-account/pages/devices";
import MobileTrackerButton from "../../common/MobileTrackerButton";

const AddDeviceForm = ({ loading, handleSubmit }) => {
  const [deviceCode, setDeviceCode] = useState("");
  const [devicePassword, setDevicePassword] = useState("");

  const { deviceFormContainer } = makeDevicesStyle();
  const onDeviceCodeChange = (event) => {
    setDeviceCode(event.target.value);
  };

  const onDevicePasswordChange = (event) => {
    setDevicePassword(event.target.value);
  };

  return (
    <form onSubmit={handleSubmit} className={deviceFormContainer}>
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
      </MobileTrackerTableContainer>
      <MobileTrackerButton
        textOnDisable="Pairing device..."
        text="Add device"
        disabled={loading}
      />
    </form>
  );
};

AddDeviceForm.propTypes = {
  loading: PropTypes.bool.isRequired,
  handleSubmit: PropTypes.func.isRequired,
};

export default AddDeviceForm;
