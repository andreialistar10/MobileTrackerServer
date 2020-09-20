import React from "react";
import Button from "@material-ui/core/Button";
import PropTypes from "prop-types";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";

const MobileTrackerButton = ({
  disabled,
  text,
  textOnDisable,
  type,
  className,
  ...props
}) => {
  const { button } = makeSharedStyle();
  const classes = !className ? button : `${button} ${className}`;
  return (
    <Button
      type={type}
      disabled={disabled}
      variant="contained"
      className={classes}
      {...props}
    >
      {disabled ? textOnDisable : text}
    </Button>
  );
};

MobileTrackerButton.propTypes = {
  disabled: PropTypes.bool.isRequired,
  text: PropTypes.string.isRequired,
  textOnDisable: PropTypes.string.isRequired,
  type: PropTypes.string,
  className: PropTypes.string,
};

MobileTrackerButton.defaultProps = {
  type: "submit",
};

export default MobileTrackerButton;
