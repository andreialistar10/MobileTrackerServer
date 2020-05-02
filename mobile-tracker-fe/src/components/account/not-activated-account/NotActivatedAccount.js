import React, { useState } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { NOT_ACTIVATED_ACCOUNT } from "../../../utils/auth/roles";
import { Redirect } from "react-router-dom";
import "./NotActivatedAccount.css";
import { resendConfirmationEmail } from "../../../redux/actions/userActions";
import { toast } from "react-toastify";
import AccountCircularProgress from "../../common/AccountCircularProgress";

const NotActivatedAccount = ({
  username,
  loggedIn,
  resendConfirmationEmail,
}) => {
  const [isLoading, setIsLoading] = useState(false);
  const handleResendEmail = (event) => {
    event.preventDefault();
    setIsLoading(true);
    resendConfirmationEmail()
      .then(() =>
        toast.success(
          "Your confirmation mail was sent! Please check your email and activate your account!"
        )
      )
      .catch((error) => toast.error(error.message, { autoClose: false }));
  };
  return !loggedIn ? (
    <Redirect to="/" />
  ) : (
    <div className="wrapper">
      {isLoading ? (
        <AccountCircularProgress />
      ) : (
        <div className="positioning-message-box">
          <div className="message-box">
            <h1 className="header-message-box">
              Your account is not yet activated!
            </h1>
            <p className="paragraph-message-box">
              Hello, dear <strong>{username}</strong>!
            </p>
            <p className="paragraph-message-box">
              You cannot use our features until you activate your account by
              clicking the confirmation link sent to the email with which you
              created your account. If you have not received any email, please
              click{" "}
              <a
                className="anchor-message-box"
                href="#"
                onClick={handleResendEmail}
              >
                {"here"}
              </a>
              .
            </p>
          </div>
        </div>
      )}
    </div>
  );
};

NotActivatedAccount.propTypes = {
  username: PropTypes.string,
  loggedIn: PropTypes.bool.isRequired,
  resendConfirmationEmail: PropTypes.func.isRequired,
};

const mapStateToProps = ({ authorization }) => {
  return {
    username: authorization.username,
    loggedIn:
      authorization.role === NOT_ACTIVATED_ACCOUNT && authorization.jwt != null,
  };
};

const mapDispatchToProps = {
  resendConfirmationEmail,
};
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NotActivatedAccount);
