import React from "react";
import TextField from "@material-ui/core/TextField";
import { makeSharedStyle } from "../../../../style/activated-account/shared";
import PropTypes from "prop-types";

const MobileTrackerTextField = ({
  textLabel,
  value,
  onValueChange,
  readOnly,
}) => {
  const { textField, input, label, primitiveInput, cell } = makeSharedStyle();
  return (
    <>
      <div className={label}>{textLabel}</div>
      <div className={cell}>
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
      </div>
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
