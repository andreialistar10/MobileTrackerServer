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
  labelProps,
  textFieldProps,
  className,
  name,
  ...props
}) => {
  const { textField, input, primitiveInput } = makeSharedStyle();
  const classes = !className ? textField : `${textField} ${className}`;
  const inputProps = {
    className: primitiveInput,
    autoComplete: "off",
  };
  if (name) {
    inputProps.name = name;
  }
  return (
    <>
      <MobileTrackerLabel textLabel={textLabel} {...labelProps} />
      <MobileTrackerCell {...textFieldProps}>
        <TextField
          disabled={readOnly}
          value={value}
          onChange={onValueChange}
          className={classes}
          inputProps={inputProps}
          InputProps={{
            readOnly: readOnly,
            disableUnderline: true,
            className: input,
          }}
          {...props}
        />
      </MobileTrackerCell>
    </>
  );
};

MobileTrackerTextField.propTypes = {
  textLabel: PropTypes.string.isRequired,
  value: PropTypes.string.isRequired,
  onValueChange: PropTypes.func.isRequired,
  labelProps: PropTypes.object,
  textFieldProps: PropTypes.object,
  readOnly: PropTypes.bool,
  className: PropTypes.string,
  name: PropTypes.string,
};

MobileTrackerTextField.defaultProps = {
  readOnly: false,
  labelProps: {},
  textFieldProps: {},
};

export default MobileTrackerTextField;
