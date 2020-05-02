import MuiAlert from "@material-ui/lab/Alert";
import React from "react";

export const MobileTrackerAlert = (props) => {
  return (
    <MuiAlert elevation={6} variant="filled" {...props} closeText="Close" />
  );
};
