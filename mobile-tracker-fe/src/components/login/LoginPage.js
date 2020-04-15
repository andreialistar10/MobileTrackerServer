import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { login } from "../../redux/actions/authorizationAction";
import { connect } from "react-redux";
import './LoginPage.css';

const LoginPage = ({ authorization }) => {
  const { jwt, refreshToken } = authorization;
  return (
    <div className="bg">
      <p>Jwt: {jwt}</p>
      <p>RefreshToken: {refreshToken}</p>
    </div>
  );
};

const mapStateToProps = (state) => {
  return {
    authorization: state.authorization,
  };
};

const mapDispatchToProps = {
  login,
};

LoginPage.propTypes = {
  authorization: PropTypes.exact({
    jwt: PropTypes.string,
    refreshToken: PropTypes.string,
  }).isRequired,
};

export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);
