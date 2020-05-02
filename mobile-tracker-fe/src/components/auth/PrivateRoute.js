import React from "react";
import { Redirect, Route } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { ACTIVATED_ACCOUNT } from "../../utils/auth/roles";

const PrivateRoute = ({ loggedIn, component: Component, ...rest }) => (
  <Route
    {...rest}
    render={(props) =>
      loggedIn ? <Component {...props} /> : <Redirect to="/" />
    }
  />
);

PrivateRoute.propTypes = {
  loggedIn: PropTypes.bool.isRequired,
  component: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => {
  const { role, refreshToken } = state.authorization;
  return {
    loggedIn: refreshToken != null && role === ACTIVATED_ACCOUNT,
  };
};

export default connect(mapStateToProps)(PrivateRoute);
