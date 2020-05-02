import React from "react";
import CircularProgress from "@material-ui/core/CircularProgress";
import "./AccountCircularProgress.css";
import PropTypes from "prop-types";

const AccountCircularProgress = (props) => {
  const style = props.style || circularProgressStyle;
  return (
    <div className="progress-bar-div-container">
      <div className="progress-bar-div">
        <CircularProgress {...props} style={style} />
      </div>
    </div>
  );
};

AccountCircularProgress.propTypes = {
  style: PropTypes.object,
};

const circularProgressStyle = {
  width: "100%",
  height: "auto",
};

export default AccountCircularProgress;
