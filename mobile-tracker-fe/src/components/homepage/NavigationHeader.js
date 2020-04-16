import React from "react";
import PropTypes from "prop-types";
import "./NavigationHeader.css";
import Button from "@material-ui/core/Button";
import { makeButtonStyle } from "../../style/homepage";

const NavigationHeader = ({ selectedPage, onMenuChanged }) => {
  const { leftButton, rightButton } = makeButtonStyle();
  return (
    <div className="navigation">
      <Button
        id="sign-in"
        disabled={selectedPage === "sign-in"}
        className={leftButton}
        onClick={() => {
          onMenuChanged("sign-in");
        }}
      >
        Sign In
      </Button>
      <Button
        id="sign-up"
        disabled={selectedPage === "sign-up"}
        className={rightButton}
        onClick={() => {
          onMenuChanged("sign-up");
        }}
      >
        Sing Up
      </Button>
    </div>
  );
};

NavigationHeader.propTypes = {
  selectedPage: PropTypes.string.isRequired,
  onMenuChanged: PropTypes.func.isRequired,
};
export default NavigationHeader;
