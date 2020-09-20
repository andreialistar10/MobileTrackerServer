import React from "react";
import Dialog from "@material-ui/core/Dialog";
import CircularProgress from "@material-ui/core/CircularProgress";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core";
import PropTypes from "prop-types";
import {
  MEDIUM_DEVICE_MAX_WIDTH,
  SMALL_DEVICE_MAX_WIDTH,
} from "../../../../../style/activated-account/constants";

const theme = createMuiTheme({
  // palette: {
  //   primary: {
  //     // main: "#767c82",
  //   },
  // },
  overrides: {
    MuiPaper: {
      root: {
        width: "70px",
        height: "70px",
        backgroundColor: "transparent",
        overflow: "hidden",
        [`@media (max-width:1200px)`]: {
          width: "65px",
          height: "65px",
        },
        [`@media (max-width:${MEDIUM_DEVICE_MAX_WIDTH})`]: {
          width: "60px",
          height: "60px",
        },
        [`@media (max-width:650px)`]: {
          width: "55px",
          height: "55px",
        },
        [`@media (max-width:${SMALL_DEVICE_MAX_WIDTH})`]: {
          width: "50px",
          height: "50px",
        },
      },
      elevation24: {
        boxShadow: "none",
      },
    },
    MuiDialog: {
      paper: {
        overflowY: "hidden !important",
      },
    },
  },
});

const MobileTrackerModalLoadingIndicator = ({ loading }) => {
  return (
    <MuiThemeProvider theme={theme}>
      <Dialog open={loading} onClose={() => {}}>
        <CircularProgress style={{ width: "100%", height: "100%" }} />
      </Dialog>
    </MuiThemeProvider>
  );
};
MobileTrackerModalLoadingIndicator.propTypes = {
  loading: PropTypes.bool.isRequired,
};
export default MobileTrackerModalLoadingIndicator;
