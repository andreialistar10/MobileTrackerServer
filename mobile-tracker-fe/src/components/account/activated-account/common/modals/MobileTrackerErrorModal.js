import React from "react";
import MobileTrackerModal from "./MobileTrackerModal";
import PropTypes from "prop-types";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core";
import {
  COLOR_TITLE_PAGE,
  FONT_FAMILY,
} from "../../../../../style/activated-account/constants";
import { makeErrorModalStyles } from "../../../../../style/activated-account/errorModal";
import Button from "@material-ui/core/Button";

const theme = createMuiTheme({
  palette: {
    primary: { main: COLOR_TITLE_PAGE },
  },
  overrides: {
    MuiDialogContent: {
      root: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: "100%",
        padding: 0,
        margin: 0,
        "&:first-child": {
          paddingTop: 0,
        },
      },
    },
    MuiButton: {
      containedPrimary: {
        borderRadius: "15px",
        backgroundColor: "#eae3e3",
        minWidth: "35px",
        fontFamily: FONT_FAMILY,
        fontSize: "1.3rem",
        fontWeight: "700",
        color: "rgb(3, 106, 161)",
        "&:hover": {
          color: "#eae3e3",
        },
      },
    },
  },
});
const MobileTrackerErrorModal = ({
  isOpen,
  handleOnClose,
  title,
  closeIsDisabled,
  className,
  closeButton,
  message,
}) => {
  const {
    defaultErrorModalBody,
    errorHeader,
    errorHeaderWrapper,
    messageError,
    belowHeaderWrapper,
  } = makeErrorModalStyles();
  const classes = !className
    ? defaultErrorModalBody
    : `${defaultErrorModalBody} ${className}`;
  const errorHeaderText = title ? title : "Error";
  return (
    <MuiThemeProvider theme={theme}>
      <MobileTrackerModal
        handleOnClose={handleOnClose}
        isOpen={isOpen}
        closeIsDisabled={closeIsDisabled}
        className={classes}
        closeButton={closeButton}
      >
        <div className={errorHeaderWrapper}>
          <h1 className={errorHeader}>{errorHeaderText}</h1>
          <p className={messageError}>{message}</p>
        </div>
        <div className={belowHeaderWrapper}>
          <Button variant={"contained"} color="primary" onClick={handleOnClose}>
            Close
          </Button>
        </div>
      </MobileTrackerModal>
    </MuiThemeProvider>
  );
};

MobileTrackerErrorModal.propTypes = {
  isOpen: PropTypes.bool.isRequired,
  handleOnClose: PropTypes.func.isRequired,
  className: PropTypes.string,
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
  closeIsDisabled: PropTypes.bool,
  title: PropTypes.string,
  closeButton: PropTypes.bool,
  message: PropTypes.string,
};

MobileTrackerErrorModal.defaultProps = {
  closeButton: false,
};

export default MobileTrackerErrorModal;
