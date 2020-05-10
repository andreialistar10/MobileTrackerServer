import React from "react";
import Button from "@material-ui/core/Button";
import PropTypes from "prop-types";
import { makeSharedStyle } from "../../../../style/activated-account/shared";

const MobileTrackerButton = ({
  disabled,
  text,
  textOnDisable,
  type,
  ...props
}) => {
  const { button } = makeSharedStyle();
  return (
    <Button
      type={type}
      disabled={disabled}
      variant="contained"
      className={button}
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
};

MobileTrackerButton.defaultProps = {
  type: "submit",
};

export default MobileTrackerButton;
