import React from "react";
import Dialog from "@material-ui/core/Dialog";
import PropTypes from "prop-types";
import DialogContent from "@material-ui/core/DialogContent";
import { makeSharedStyle } from "../../../../../style/activated-account/shared";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";

const MobileTrackerModal = ({
  isOpen,
  title,
  handleOnClose,
  className,
  children,
  closeIsDisabled,
}) => {
  const {
    defaultModalBody,
    modalTitle,
    modalWrapper,
    modalCloseIcon,
  } = makeSharedStyle();
  const classes = !className
    ? defaultModalBody
    : `${defaultModalBody} ${className}`;

  return (
    <Dialog
      open={isOpen}
      onClose={handleOnClose}
      maxWidth={false}
      PaperProps={{ className: classes }}
    >
      <div className={modalWrapper}>
        <h1 className={modalTitle}>{title}</h1>
        <DialogContent>{children}</DialogContent>
        <IconButton
          onClick={handleOnClose}
          className={modalCloseIcon}
          disabled={closeIsDisabled}
        >
          <CloseIcon />
        </IconButton>
      </div>
    </Dialog>
  );
};

MobileTrackerModal.propTypes = {
  isOpen: PropTypes.bool.isRequired,
  closeIsDisabled: PropTypes.bool,
  title: PropTypes.string.isRequired,
  handleOnClose: PropTypes.func.isRequired,
  className: PropTypes.string,
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]),
};

MobileTrackerModal.defaultProps = {
  closeIsDisabled: false,
};

export default MobileTrackerModal;
