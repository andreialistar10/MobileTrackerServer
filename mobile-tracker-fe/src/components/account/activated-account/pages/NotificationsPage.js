import React from "react";
import NotificationsNoneOutlinedIcon from "@material-ui/icons/NotificationsNoneOutlined";
import { makeSharedStyle } from "../../../../style/activated-account/shared";

const NotificationsPage = () => {
  const { behindContent, icon } = makeSharedStyle();
  return (
    <div>
      <h3>NotificationsPage</h3>
      <div className={behindContent}>
        <NotificationsNoneOutlinedIcon className={icon} />
      </div>
    </div>
  );
};

export default NotificationsPage;
