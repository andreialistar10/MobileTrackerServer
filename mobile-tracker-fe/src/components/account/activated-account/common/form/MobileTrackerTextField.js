import React from "react";
import TextField from "@material-ui/core/TextField";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import PropTypes from "prop-types";
import MobileTrackerCell from "../containers/MobileTrackerCell";
import MobileTrackerLabel from "./MobileTrackerLabel";
import { createMuiTheme, MuiThemeProvider, Tooltip } from "@material-ui/core";
import { SMALL_DEVICE_MAX_WIDTH } from "../../../../../style/activated-account/constants";
import InputAdornment from "@material-ui/core/InputAdornment";
import ErrorRoundedIcon from "@material-ui/icons/ErrorRounded";

const theme = createMuiTheme({
  overrides: {
    MuiTooltip: {
      tooltipPlacementBottom: {
        backgroundColor: "#be4b49",
        color: "white",
        fontSize: "0.75rem",
        [`@media (max-width:850px)`]: {
          fontSize: "0.72rem",
          marginLeft: "calc(0.5rem - 8px)",
        },
        [`@media (max-width:800px)`]: {
          fontSize: "0.69rem",
          marginLeft: "calc(0.5rem - 8px)",
        },
        [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
          fontSize: "0.62rem",
          marginLeft: "calc(0.5rem - 8px)",
        },
      },
      arrow: {
        color: "#be4b49",
      },
    },
  },
});

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
    errorIcon,
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
      <InputAdornment position="end" className={errorIcon}>
        <ErrorRoundedIcon />
      </InputAdornment>
    );
  }

  return (
    <>
      <MuiThemeProvider theme={theme}>
        <MobileTrackerLabel textLabel={textLabel} {...labelProps} />
        <MobileTrackerCell {...textFieldProps}>
          <Tooltip
            title={errorMessage}
            placement={"bottom"}
            arrow
            open={openErrorTooltip}
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
      </MuiThemeProvider>
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
