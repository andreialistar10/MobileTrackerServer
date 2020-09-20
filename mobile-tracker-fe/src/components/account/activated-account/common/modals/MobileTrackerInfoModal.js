import React from "react";
import MobileTrackerModal from "./MobileTrackerModal";
import PropTypes from "prop-types";
import makeInfoModalStyles from "../../../../../style/activated-account/infoModal";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core";
import CheckCircleOutlineIcon from "@material-ui/icons/CheckCircleOutline";
import {
  COLOR_TITLE_PAGE,
  FONT_FAMILY,
} from "../../../../../style/activated-account/constants";
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
        paddingLeft: "60px",
        paddingRight: "60px",
        borderRadius: "18px",
        backgroundColor: "#eae3e3",
        minWidth: "60px",
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

const MobileTrackerInfoModal = ({
  handleOnClose,
  isOpen,
  title,
  message,
  ...props
}) => {
  const {
    infoModalWrapper,
    topInfoModal,
    bottomInfoModal,
    headerInfoModal,
    svgImage,
    messageInfoModal,
  } = makeInfoModalStyles();
  return (
    <MuiThemeProvider theme={theme}>
      <MobileTrackerModal
        handleOnClose={handleOnClose}
        isOpen={isOpen}
        className={infoModalWrapper}
        {...props}
      >
        <div className={topInfoModal}>
          <CheckCircleOutlineIcon className={svgImage} />
        </div>
        <div className={bottomInfoModal}>
          <h1 className={headerInfoModal}>{title}</h1>
          <p className={messageInfoModal}>{message}</p>
          <Button variant={"contained"} color="primary" onClick={handleOnClose}>
            OK
          </Button>
        </div>
      </MobileTrackerModal>
    </MuiThemeProvider>
  );
};

MobileTrackerInfoModal.propTypes = {
  isOpen: PropTypes.bool.isRequired,
  handleOnClose: PropTypes.func.isRequired,
  className: PropTypes.string,
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
  title: PropTypes.string,
  closeButton: PropTypes.bool,
  message: PropTypes.string,
};

MobileTrackerInfoModal.defaultProps = {
  closeButton: false,
};

export default MobileTrackerInfoModal;
