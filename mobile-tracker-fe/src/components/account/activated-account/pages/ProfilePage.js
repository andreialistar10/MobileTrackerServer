import React, { useCallback, useEffect, useState } from "react";
import PersonOutlineIcon from "@material-ui/icons/PersonOutline";
import { makeSharedStyle } from "../../../../style/activated-account/shared";
import MobileTrackerTableContainer from "../common/containers/MobileTrackerTableContainer";
import MobileTrackerRow from "../common/containers/MobileTrackerRow";
import MobileTrackerTextField from "../common/form/MobileTrackerTextField";
import MobileTrackerButton from "../common/form/MobileTrackerButton";
import { toast } from "react-toastify";
import { getUserDetails, updateUserDetails } from "../../../../api/userApi";
import MobileTrackerModalLoadingIndicator from "../common/modals/MobileTrackerModalLoadingIndicator";
import { validateName } from "../../../../utils/validation";

const initialProfileDetails = {
  username: "",
  email: "",
  firstName: "",
  lastName: "",
};

const initialErrorMessages = {
  firstName: "",
  lastName: "",
};

const validator = {
  firstName: (firstNameValue) => {
    const message = validateName(firstNameValue, "First name");
    return message === undefined ? "" : message;
  },
  lastName: (lastNameValue) => {
    const message = validateName(lastNameValue, "Last name");
    return message === undefined ? "" : message;
  },
};

const ProfilePage = () => {
  const {
    behindContent,
    icon,
    inlineFormLabel,
    inlineFormTextField,
    inlineFormButton,
    inlineFormMaterialUITextField,
    whiteColor,
  } = makeSharedStyle();
  const labelProps = { className: inlineFormLabel };
  const rightSideTextFieldProps = { className: inlineFormLabel };
  const leftSideTextFieldProps = {
    className: `${inlineFormLabel} ${inlineFormTextField}`,
  };
  const [profileDetails, setProfileDetails] = useState({
    ...initialProfileDetails,
  });
  const [errors, setErrors] = useState({
    ...initialErrorMessages,
  });
  const [loading, setLoading] = useState(true);
  const [updateProfile, setUpdateProfile] = useState(false);
  const [selectedInput, setSelectedInput] = useState("");

  const validateInput = useCallback(
    (field, fieldValue) => {
      setErrors((prevState) => {
        const error = validator[field](fieldValue);
        if (prevState[field] !== error) {
          const newErrors = { ...prevState };
          newErrors[field] = error;
          return newErrors;
        }
        return prevState;
      });
    },
    [errors, setErrors]
  );

  const onValueChange = (event) => {
    const { name, value } = event.target;
    validateInput(name, value);
    setProfileDetails((prevState) => {
      const nextState = { ...prevState };
      nextState[name] = value;
      return nextState;
    });
  };

  const handleOnFocus = (event) => {
    setSelectedInput(event.target.name);
  };

  const handleOnSubmit = (event) => {
    event.preventDefault();
    if (formIsValid()) {
      setLoading(true);
      setUpdateProfile(true);
    } else {
      toast.error("Please fill the invalid fields!");
    }
  };

  const formIsValid = () => {
    const { firstName, lastName } = errors;
    return firstName === "" && lastName === "";
  };

  useEffect(() => {
    if (updateProfile) {
      const { lastName, firstName } = profileDetails;
      updateUserDetails({ lastName, firstName })
        .then((userDetails) => {
          setProfileDetails((prevState) => {
            return {
              ...prevState,
              ...userDetails,
            };
          });
          toast.success("You successfully update your profile!");
        })
        .catch((error) => {
          toast.error(error.message);
        })
        .finally(() => {
          setLoading(false);
          setUpdateProfile(false);
        });
    }
  }, [updateProfile, setLoading]);
  useEffect(() => {
    getUserDetails()
      .then((response) => {
        setProfileDetails(response);
      })
      .catch((error) => {
        toast.error(error.message);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);
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
              onFocus={handleOnFocus}
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
              onFocus={handleOnFocus}
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
              inputClassName={whiteColor}
              errorMessage={errors.firstName}
              onFocus={handleOnFocus}
              openErrorTooltip={selectedInput === "firstName"}
            />
            <MobileTrackerTextField
              value={profileDetails.lastName}
              textLabel={"Last Name:"}
              onValueChange={onValueChange}
              labelProps={labelProps}
              textFieldProps={rightSideTextFieldProps}
              className={inlineFormMaterialUITextField}
              name="lastName"
              inputClassName={whiteColor}
              errorMessage={errors.lastName}
              onFocus={handleOnFocus}
              openErrorTooltip={selectedInput === "lastName"}
            />
          </MobileTrackerRow>
        </MobileTrackerTableContainer>
        <MobileTrackerButton
          textOnDisable="Update profile"
          text="Update profile"
          disabled={!formIsValid()}
          className={inlineFormButton}
        />
      </form>
      <MobileTrackerModalLoadingIndicator loading={loading} />
    </>
  );
};

export default ProfilePage;
