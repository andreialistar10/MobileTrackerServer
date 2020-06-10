import React from "react";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import PropTypes from "prop-types";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import {FONT_FAMILY} from "../../../../../style/activated-account/constants";

const MobileTrackerAlertModal = ({
  open,
  onClose,
  handleOnAgree,
  handleOnDisagree,
  title,
  message,
  agreeButtonText,
  disagreeButtonText,
}) => {
  const { modalTitlePage } = makeSharedStyle();

  return (
    <Dialog
      open={open}
      onClose={onClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
    >
      <DialogTitle className={modalTitlePage} style={{ fontWeight: "bold" }}>
        {title}
      </DialogTitle>
      <DialogContent style={{ fontFamily: FONT_FAMILY }}>
        {message}
      </DialogContent>
      <DialogActions>
        <Button onClick={handleOnDisagree} color="primary">
          {disagreeButtonText}
        </Button>
        <Button onClick={handleOnAgree} color="primary" autoFocus>
          {agreeButtonText}
        </Button>
      </DialogActions>
    </Dialog>
  );
};
MobileTrackerAlertModal.propTypes = {
  open: PropTypes.bool.isRequired,
  title: PropTypes.string.isRequired,
  message: PropTypes.oneOfType([
    PropTypes.string,
    PropTypes.oneOfType([PropTypes.arrayOf(PropTypes.node), PropTypes.node]),
  ]).isRequired,
  handleOnAgree: PropTypes.func.isRequired,
  handleOnDisagree: PropTypes.func.isRequired,
  agreeButtonText: PropTypes.string,
  disagreeButtonText: PropTypes.string,
  onClose: PropTypes.func,
};

MobileTrackerAlertModal.defaultProps = {
  onClose: () => {},
  agreeButtonText: "Agree",
  disagreeButtonText: "Disagree",
};

export default MobileTrackerAlertModal;
