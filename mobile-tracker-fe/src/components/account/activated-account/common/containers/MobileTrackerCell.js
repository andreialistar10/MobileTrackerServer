import React from "react";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import PropTypes from "prop-types";

const MobileTrackerCell = ({ children, className, ...props }) => {
  const { cell } = makeSharedStyle();
  const classes = !className ? cell : `${cell} ${className}`;
  return (
    <div className={classes} {...props}>
      {children}
    </div>
  );
};

MobileTrackerCell.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
  className: PropTypes.string,
};

export default MobileTrackerCell;
