import React, { useState } from "react";
import PersonOutlineIcon from "@material-ui/icons/PersonOutline";
import { makeSharedStyle } from "../../../../style/activated-account/shared";
import MobileTrackerTableContainer from "../common/containers/MobileTrackerTableContainer";
import MobileTrackerRow from "../common/containers/MobileTrackerRow";
import MobileTrackerLabel from "../common/form/MobileTrackerLabel";
import MobileTrackerCell from "../common/containers/MobileTrackerCell";
import MobileTrackerTextField from "../common/form/MobileTrackerTextField";
import MobileTrackerButton from "../common/form/MobileTrackerButton";
import {toast} from "react-toastify";

const initialProfileDetails = {
  username: "username",
  email: "e-mail",
  firstName: "",
  lastName: "",
};
const ProfilePage = () => {
  const {
    behindContent,
    icon,
    inlineFormLabel,
    inlineFormTextField,
    inlineFormButton,
    inlineFormMaterialUITextField,
  } = makeSharedStyle();
  const labelProps = { className: inlineFormLabel };
  const rightSideTextFieldProps = { className: inlineFormLabel };
  const leftSideTextFieldProps = {
    className: `${inlineFormLabel} ${inlineFormTextField}`,
  };
  const [profileDetails, setProfileDetails] = useState({
    ...initialProfileDetails,
  });
  const onValueChange = (event) => {
    const { name, value } = event.target;
    setProfileDetails((prevState) => {
      const nextState = { ...prevState };
      nextState[name] = value;
      return nextState;
    });
  };

  const handleOnSubmit = (event) => {
    event.preventDefault();
    toast.success("You successfully update your profile!")
  };
  return (
    <>
      <div className={behindContent}>
        <PersonOutlineIcon className={icon} />
      </div>
      <form onSubmit={handleOnSubmit}>
        <MobileTrackerTableContainer>
          <MobileTrackerRow>
            <MobileTrackerTextField
              value={profileDetails.username}
              textLabel={"Username:"}
              onValueChange={onValueChange}
              labelProps={labelProps}
              textFieldProps={leftSideTextFieldProps}
              className={inlineFormMaterialUITextField}
              name="username"
              readOnly={true}
            />
            <MobileTrackerTextField
              value={profileDetails.email}
              textLabel={"E-mail:"}
              onValueChange={onValueChange}
              labelProps={labelProps}
              textFieldProps={rightSideTextFieldProps}
              className={inlineFormMaterialUITextField}
              name="email"
              readOnly={true}
            />
          </MobileTrackerRow>
          <MobileTrackerRow>
            <MobileTrackerTextField
              value={profileDetails.firstName}
              textLabel={"First Name:"}
              onValueChange={onValueChange}
              labelProps={labelProps}
              textFieldProps={leftSideTextFieldProps}
              className={inlineFormMaterialUITextField}
              name="firstName"
            />
            <MobileTrackerTextField
              value={profileDetails.lastName}
              textLabel={"Last Name:"}
              onValueChange={onValueChange}
              labelProps={labelProps}
              textFieldProps={rightSideTextFieldProps}
              className={inlineFormMaterialUITextField}
              name="lastName"
            />
          </MobileTrackerRow>
        </MobileTrackerTableContainer>
        <MobileTrackerButton
          textOnDisable="Update profile"
          text="Update profile"
          disabled={false}
          className={inlineFormButton}
        />
      </form>
    </>
  );
};

export default ProfilePage;
