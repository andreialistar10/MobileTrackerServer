import React from "react";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import PropTypes from "prop-types";

const MobileTrackerTableContainer = ({ children, className, ...props }) => {
  const { formContainer } = makeSharedStyle();
  const classes = !className ? formContainer : `${formContainer} ${className}`;
  return (
    <div className={classes} {...props}>
      {children}
    </div>
  );
};

MobileTrackerTableContainer.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
  className: PropTypes.string,
};

export default MobileTrackerTableContainer;
