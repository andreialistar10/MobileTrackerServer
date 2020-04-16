import { stringIsBlank } from "./index";
const validator = require("email-validator");

const usernameRegex = /^[a-z][-a-z0-9_.]*$/i;
const nameRegex = /^[a-z][-a-z ]+[a-z]$/i;

export const validateUser = (newUser) => {
  const errors = {};

  const username = validateUsername(newUser.username);
  if (username) {
    errors.username = username;
  }

  const password = validatePassword(newUser.password);
  if (password) {
    errors.password = password;
  }

  const repeatPassword = validateRepeatPassword(
    newUser.password,
    newUser.repeatPassword
  );
  if (repeatPassword) {
    errors.repeatPassword = repeatPassword;
  }

  const firstName = validateName(newUser.firstName, "First name");
  if (firstName) {
    errors.firstName = firstName;
  }
  const lastName = validateName(newUser.lastName, "Last name");
  if (lastName) {
    errors.lastName = lastName;
  }

  const email = validateEmail(newUser.email);
  if (email) {
    errors.email = email;
  }
  return errors;
};

export const validateUsername = (username) => {
  if (stringIsBlank(username)) {
    return "Username cannot be blank!";
  }
  if (!usernameRegex.test(username)) {
    return "The username must start with letter and have only alphanumeric characters, '.', '_' and '-'!";
  }
};

export const validatePassword = (password) => {
  if (stringIsBlank(password)) {
    return "Password cannot be blank!";
  }
  if (password.length < 8) {
    return "Password must be at least 8 characters long!";
  }
};

export const validateRepeatPassword = (password, repeatPassword) => {
  if (password !== repeatPassword) {
    return "Passwords do not match!";
  }
};

export const validateName = (name, fieldName) => {
  if (stringIsBlank(name)) {
    return fieldName + " cannot be blank!";
  }
  if (!nameRegex.test(name)) {
    return (
      fieldName +
      " must start and end with letter, have at least 3 characters long and contain only letters, spaces and '-'!"
    );
  }
};

export const validateEmail = (email) => {
  if (stringIsBlank(email)) {
    return "E-mail cannot be blank!";
  }
  if (!validator.validate(email)) {
    return "E-mail format is not valid!";
  }
};
