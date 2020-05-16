import React from "react";
import PropTypes from "prop-types";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";

const MobileTrackerLabel = ({ textLabel }) => {
  const { label } = makeSharedStyle();
  return <div className={label}>{textLabel}</div>;
};

MobileTrackerLabel.propTypes = {
  textLabel: PropTypes.string.isRequired,
};
export default MobileTrackerLabel;
