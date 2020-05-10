import React, { useState } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { logout } from "../../../redux/actions/authorizationActions";
import { toast } from "react-toastify";
import AccountCircularProgress from "../../common/AccountCircularProgress";
import NavigationMenu from "./NavigationMenu";
import Header from "./Header";
import { makeActivatedAccountStyle } from "../../../style/activated-account/activatedAccount";
import TitlePage from "./common/TitlePage";
import { Route, Switch } from "react-router-dom";
import ProfilePage from "./pages/ProfilePage";
import DevicesPage from "./pages/devices/DevicesPage";
import DeviceSettingsPage from "./pages/DeviceSettingsPage";
import LocationsPage from "./pages/LocationsPage";
import NotificationsPage from "./pages/NotificationsPage";

const ActivatedAccount = ({ username, logout, pageTitle }) => {
  const style = makeActivatedAccountStyle();
  const {
    root,
    header,
    belowHeader,
    rightSide,
    leftSide,
    rightSideContentWrapper,
  } = style;
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
    <AccountCircularProgress />
  ) : (
    <div className={root}>
      <div className={header}>
        <Header logout={handleLogout} />
      </div>
      <div className={belowHeader}>
        <div className={leftSide}>
          <NavigationMenu selectedPage={pageTitle} />
        </div>
        <div className={rightSide}>
          <TitlePage title={pageTitle} />
          <div className={rightSideContentWrapper}>
            <Switch>
              <Route path="/account/devices" component={DevicesPage} />
              <Route
                path="/account/device-settings"
                component={DeviceSettingsPage}
              />
              <Route path="/account/locations" component={LocationsPage} />
              <Route
                path="/account/notifications"
                component={NotificationsPage}
              />
              <Route path="/account" component={ProfilePage} />
            </Switch>
          </div>
        </div>
      </div>
    </div>
  );
};

ActivatedAccount.propTypes = {
  username: PropTypes.string,
  pageTitle: PropTypes.string.isRequired,
  logout: PropTypes.func.isRequired,
};

const mapStateToProps = (state, ownProps) => {
  const page = ownProps.match.params.page;
  const pageTitle = page || "profile";
  return {
    username: state.authorization.username,
    pageTitle,
  };
};

const mapDispatchToProps = {
  logout,
};
export default connect(mapStateToProps, mapDispatchToProps)(ActivatedAccount);
