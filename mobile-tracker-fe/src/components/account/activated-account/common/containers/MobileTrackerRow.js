import React from "react";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import PropTypes from "prop-types";

const MobileTrackerRow = ({ children, show, ...props }) => {
  const { textFieldWrapper } = makeSharedStyle();
  return show && <div className={textFieldWrapper} {...props}>{children}</div>;
};

MobileTrackerRow.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
  show: PropTypes.bool
};

MobileTrackerRow.defaultProps= {
  show: true
};

export default MobileTrackerRow;
