import React from "react";
import { NavLink } from "react-router-dom";
import { makeNavigationMenuStyle } from "../../../style/activated-account/navigationMenu";
import PersonOutlineIcon from "@material-ui/icons/PersonOutline";
import PhoneAndroidOutlinedIcon from "@material-ui/icons/PhoneAndroidOutlined";
import RoomOutlinedIcon from "@material-ui/icons/RoomOutlined";
// import NotificationsNoneOutlinedIcon from "@material-ui/icons/NotificationsNoneOutlined";
// import PhonelinkSetupOutlinedIcon from "@material-ui/icons/PhonelinkSetupOutlined";
import PropTypes from "prop-types";
const NavigationMenu = ({ selectedPage }) => {
  const style = makeNavigationMenuStyle();
  const {
    root,
    listItem,
    selectedListItem,
    list,
    anchor,
    anchorText,
    icon,
  } = style;
  const selectedListItemStyle = `${listItem} ${selectedListItem}`;
  return (
    <nav className={root}>
      <ul className={list}>
        <li
          className={
            selectedPage === "profile" ? selectedListItemStyle : listItem
          }
        >
          <NavLink to="/account" className={anchor} exact>
            <PersonOutlineIcon className={icon} />
            <span className={anchorText}>Profile</span>
          </NavLink>
        </li>
        <li
          className={
            selectedPage === "devices" ? selectedListItemStyle : listItem
          }
        >
          <NavLink to="/account/devices" className={anchor}>
            <PhoneAndroidOutlinedIcon className={icon} />
            <span className={anchorText}>Devices</span>
          </NavLink>
        </li>
        <li
          className={
            selectedPage === "locations" ? selectedListItemStyle : listItem
          }
        >
          <NavLink to="/account/locations" className={anchor}>
            <RoomOutlinedIcon className={icon} />
            <span className={anchorText}>Locations</span>
          </NavLink>
        </li>
        {/*<li*/}
        {/*  className={*/}
        {/*    selectedPage === "notifications" ? selectedListItemStyle : listItem*/}
        {/*  }*/}
        {/*>*/}
        {/*  <NavLink to="/account/notifications" className={anchor}>*/}
        {/*    <NotificationsNoneOutlinedIcon className={icon} />*/}
        {/*    <span className={anchorText}>Notifications</span>*/}
        {/*  </NavLink>*/}
        {/*</li>*/}
        {/*<li*/}
        {/*  className={*/}
        {/*    selectedPage === "device-settings"*/}
        {/*      ? selectedListItemStyle*/}
        {/*      : listItem*/}
        {/*  }*/}
        {/*>*/}
        {/*  <NavLink to="/account/device-settings" className={anchor}>*/}
        {/*    <PhonelinkSetupOutlinedIcon className={icon} />*/}
        {/*    <span className={anchorText}>Settings</span>*/}
        {/*  </NavLink>*/}
        {/*</li>*/}
      </ul>
    </nav>
  );
};

NavigationMenu.propTypes = {
  selectedPage: PropTypes.string.isRequired,
};

export default NavigationMenu;
