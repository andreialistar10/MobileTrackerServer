import React from "react";
import TextField from "@material-ui/core/TextField";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import PropTypes from "prop-types";
import MobileTrackerCell from "../containers/MobileTrackerCell";
import MobileTrackerLabel from "./MobileTrackerLabel";

const MobileTrackerTextField = ({
  textLabel,
  value,
  onValueChange,
  readOnly,
}) => {
  const { textField, input, primitiveInput } = makeSharedStyle();
  return (
    <>
      <MobileTrackerLabel textLabel={textLabel} />
      <MobileTrackerCell>
        <TextField
          disabled={readOnly}
          value={value}
          onChange={onValueChange}
          className={textField}
          inputProps={{
            className: primitiveInput,
          }}
          InputProps={{
            readOnly: readOnly,
            disableUnderline: true,
            className: input,
          }}
        />
      </MobileTrackerCell>
    </>
  );
};

MobileTrackerTextField.propTypes = {
  textLabel: PropTypes.string.isRequired,
  value: PropTypes.string.isRequired,
  onValueChange: PropTypes.func.isRequired,
  readOnly: PropTypes.bool,
};

MobileTrackerTextField.defaultProps = {
  readOnly: false,
};

export default MobileTrackerTextField;
