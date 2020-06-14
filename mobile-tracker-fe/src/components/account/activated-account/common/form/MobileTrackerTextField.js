import React from "react";
import TextField from "@material-ui/core/TextField";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import PropTypes from "prop-types";
import MobileTrackerCell from "../containers/MobileTrackerCell";
import MobileTrackerLabel from "./MobileTrackerLabel";
import { Tooltip } from "@material-ui/core";
import InputAdornment from "@material-ui/core/InputAdornment";
import ErrorRoundedIcon from "@material-ui/icons/ErrorRounded";

const MobileTrackerTextField = ({
  textLabel,
  value,
  onValueChange,
  readOnly,
  labelProps,
  textFieldProps,
  className,
  name,
  inputClassName,
  errorMessage,
  openErrorTooltip,
  ...props
}) => {
  const {
    textField,
    input,
    primitiveInput,
    inputError,
    errorColor,
    tooltipError,
  } = makeSharedStyle();
  const classes = !className ? textField : `${textField} ${className}`;
  const inputProps = {
    className: !inputClassName
      ? primitiveInput
      : `${primitiveInput} ${inputClassName}`,
    autoComplete: "off",
  };

  const wrapperInputClassName =
    !openErrorTooltip && errorMessage !== "" ? `${input} ${inputError}` : input;
  if (name) {
    inputProps.name = name;
  }

  const InputProps = {
    readOnly: readOnly,
    disableUnderline: true,
    className: wrapperInputClassName,
  };
  if (!openErrorTooltip && errorMessage !== "") {
    InputProps.endAdornment = (
      <InputAdornment position="end" className={errorColor}>
        <ErrorRoundedIcon />
      </InputAdornment>
    );
  }

  return (
    <>
      <MobileTrackerLabel textLabel={textLabel} {...labelProps} />
      <MobileTrackerCell {...textFieldProps}>
        <Tooltip
          title={errorMessage}
          placement={"bottom"}
          arrow
          open={openErrorTooltip}
          classes={{
            tooltipPlacementBottom: tooltipError,
            arrow: errorColor,
          }}
        >
          <TextField
            disabled={readOnly}
            value={value}
            onChange={onValueChange}
            className={classes}
            inputProps={inputProps}
            InputProps={InputProps}
            {...props}
          />
        </Tooltip>
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
  inputClassName: PropTypes.string,
  errorMessage: PropTypes.string,
  openErrorTooltip: PropTypes.bool,
};

MobileTrackerTextField.defaultProps = {
  readOnly: false,
  labelProps: {},
  textFieldProps: {},
  errorMessage: "",
  openErrorTooltip: false,
};

export default MobileTrackerTextField;
