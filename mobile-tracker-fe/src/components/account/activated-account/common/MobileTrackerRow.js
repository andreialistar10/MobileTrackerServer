import React from "react";
import { makeSharedStyle } from "../../../../style/activated-account/shared";
import PropTypes from "prop-types";

const MobileTrackerRow = ({ children }) => {
  const { textFieldWrapper } = makeSharedStyle();
  return <div className={textFieldWrapper}>{children}</div>;
};

MobileTrackerRow.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
};

export default MobileTrackerRow;
