import React from "react";
import { makeHeaderStyle } from "../../../style/activated-account/header";
import ExitToAppOutlinedIcon from "@material-ui/icons/ExitToAppOutlined";
import PropTypes from "prop-types";
import IconButton from "@material-ui/core/IconButton";

const Header = ({ logout }) => {
  const style = makeHeaderStyle();
  const {
    root,
    logo,
    header,
    prefixHeaderText,
    suffixHeaderText,
    headerText,
    logoutButtonContainer,
    logoutButton,
    logoutIcon,
  } = style;
  return (
    <div className={root}>
      <img
        src="http://localhost:3002/images/Logo.png"
        alt="Logo"
        className={logo}
      />
      <h1 className={header}>
        <span className={`${headerText} ${prefixHeaderText}`}>Mobile</span>
        <span className={`${headerText} ${suffixHeaderText}`}>Tracker</span>
      </h1>
      <div className={logoutButtonContainer}>
        <IconButton
          aria-label="Sign out"
          className={logoutButton}
          onClick={logout}
        >
          <ExitToAppOutlinedIcon className={logoutIcon} />
        </IconButton>
      </div>
    </div>
  );
};

Header.propTypes = {
  logout: PropTypes.func.isRequired,
};

export default Header;
