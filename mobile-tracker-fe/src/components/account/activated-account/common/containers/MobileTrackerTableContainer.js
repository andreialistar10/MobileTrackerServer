import React from "react";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import PropTypes from "prop-types";

const MobileTrackerTableContainer = ({ children }) => {
  const { formContainer } = makeSharedStyle();
  return <div className={formContainer}>{children}</div>;
};

MobileTrackerTableContainer.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
};

export default MobileTrackerTableContainer;
