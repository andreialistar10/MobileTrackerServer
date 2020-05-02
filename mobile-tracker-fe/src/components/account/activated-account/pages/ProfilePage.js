import React from "react";
import PersonOutlineIcon from "@material-ui/icons/PersonOutline";
import { makeSharedStyle } from "../../../../style/activated-account/shared";

const ProfilePage = () => {
  const { behindContent, icon } = makeSharedStyle();
  return (
    <div>
      <h3>ProfilePage</h3>
      <div className={behindContent}>
        <PersonOutlineIcon className={icon} />
      </div>
    </div>
  );
};

export default ProfilePage;
