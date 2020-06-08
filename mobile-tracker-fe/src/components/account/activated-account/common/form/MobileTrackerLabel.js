import React from "react";
import PropTypes from "prop-types";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";

const MobileTrackerLabel = ({ textLabel, className, ...props }) => {
  const { label } = makeSharedStyle();
  const classes = !className ? label : `${label} ${className}`;
  return <div className={classes} {...props}>{textLabel}</div>;
};

MobileTrackerLabel.propTypes = {
  textLabel: PropTypes.string.isRequired,
  className: PropTypes.string
};
export default MobileTrackerLabel;
