import React, { useState } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { logout } from "../../redux/actions/authorizationActions";
import { toast } from "react-toastify";
import "./Account.css";
import AccountCircularProgress from "../common/AccountCircularProgress";

const Account = ({ username, logout }) => {
  const [loading, setLoading] = useState(false);
  const handleLogout = () => {
    setLoading(true);
    logout()
      .then(() => toast.success("You successfully logged out! See you soon!"))
      .catch((error) => {
        setLoading(false);
        toast.error(error.message, { autoClose: false });
      });
  };
  return loading ? (
    <AccountCircularProgress/>
  ) : (
    <div>
      <h1>Activated account page!</h1>
      <p>Hello, {username}</p>
      <button
        onClick={() => {
          handleLogout();
        }}
      >
        Logout
      </button>
    </div>
  );
};

Account.propTypes = {
  username: PropTypes.string,
  logout: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => {
  return {
    username: state.authorization.username,
  };
};

const mapDispatchToProps = {
  logout,
};
export default connect(mapStateToProps, mapDispatchToProps)(Account);
