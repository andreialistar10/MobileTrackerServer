import React from "react";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import PropTypes from "prop-types";

const MobileTrackerCell = ({ children }) => {
  const { cell } = makeSharedStyle();
  return <div className={cell}>{children}</div>;
};

MobileTrackerCell.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
};

export default MobileTrackerCell;
