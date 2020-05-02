import React, { useEffect } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import LinearProgress from "@material-ui/core/LinearProgress";
import { ACTIVATED_ACCOUNT, NOT_ACTIVATED_ACCOUNT } from "../../utils/auth/roles";

const AuthLoading = ({ role, refreshToken, jwt, history }) => {
  useEffect(() => {
    if (refreshToken && role === ACTIVATED_ACCOUNT) {
      history.push("/account");
    } else if (jwt && role === NOT_ACTIVATED_ACCOUNT) {
      history.push("/not-activated-account");
    } else {
      history.push("/sign-in");
    }
  }, []);
  return <LinearProgress style={{ width: "100%" }} />;
};

AuthLoading.propTypes = {
  jwt: PropTypes.string,
  refreshToken: PropTypes.string,
  role: PropTypes.string,
  history: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => {
  const { role, refreshToken, jwt } = state.authorization;
  return {
    role,
    refreshToken,
    jwt,
  };
};

export default connect(mapStateToProps)(AuthLoading);
