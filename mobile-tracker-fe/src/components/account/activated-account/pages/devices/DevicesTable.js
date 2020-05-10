import React from "react";
import MaterialTable from "material-table";
import { COLOR_TITLE_PAGE } from "../../../../../style/activated-account/constants";
import { compose, setDisplayName } from "recompose";
import AddCircleIcon from "@material-ui/icons/AddCircle";
import { makeDevicesStyle } from "../../../../../style/activated-account/pages/devices";
import PropTypes from "prop-types";

const DevicesTable = ({ handleOnAdd }) => {
  const myTextIcon = React.useRef(null);
  const wrapperStyle = {
    backgroundColor: "rgba(255,255,255,0.6)",
  };

  const { deviceTableAddButton, deviceTableAddButtonText } = makeDevicesStyle();

  const headerStyle = {
    color: COLOR_TITLE_PAGE,
    textTransform: "uppercase",
    fontWeight: "bold",
    fontFamily: "Rubik-Regular",
  };
  const cellStyle = {
    fontFamily: "Rubik-Regular",
    fontWeight: "500",
    fontSize: "0.9rem",
  };
  const [state, setState] = React.useState({
    columns: [
      {
        title: "device code",
        field: "id",
        sorting: false,
        editable: "never",
        searchable: false,
      },
      { title: "device name", field: "name" },
      {
        title: "date of registration",
        field: "date",
        editable: "never",
        searchable: false,
      },
    ],
    data: [
      {
        id: "MOTR_$4234230423423942304923402394_4324234",
        name: "Baran",
        date: new Date().toDateString(),
      },
      {
        id: "MOTR_$4234230423423942304923402394_4324234",
        name: "Baran",
        date: new Date(23).toDateString(),
      },
      {
        id: "MOTR_$4234230423423942304923402394_4324234",
        name: "Baran",
        date: new Date(23).toDateString(),
      },
      {
        id: "MOTR_$4234230423423942304923402394_4324234",
        name: "Baran",
        date: new Date(23).toDateString(),
      },
      {
        id: "MOTR_$4234230423423942304923402394_4324234",
        name: "Baran",
        date: new Date(23).toDateString(),
      },
      {
        id: "MOTR_$4234230423423942304923402394_4324234",
        name: "Baran",
        date: new Date(23).toDateString(),
      },
    ],
  });

  React.useEffect(() => {
    // removing the class 'MuiIconButton-root' from parent of parent of our ref node.

    myTextIcon.current.parentNode.parentNode.classList.remove(
      "MuiIconButton-root"
    );
  }, []);

  return (
    <MaterialTable
      columns={state.columns}
      data={state.data}
      style={wrapperStyle}
      options={{
        minBodyHeight: 295,
        maxBodyHeight: 295,
        doubleHorizontalScroll: true,
        emptyRowsWhenPaging: false,
        draggable: false,
        showTitle: false,
        selection: true,
        headerStyle,
        cellStyle,
      }}
      localization={{
        body: {
          emptyDataSourceMessage: "No registered device",
        },
      }}
      actions={[
        {
          icon: compose(setDisplayName("AddButton"))(() => {
            return (
              <div className={deviceTableAddButton} ref={myTextIcon}>
                <AddCircleIcon />
                <span className={deviceTableAddButtonText}>Add new device</span>
              </div>
            );
          }),
          tooltip: "Add new device",
          isFreeAction: true,
          onClick: handleOnAdd,
        },
      ]}
      editable={{
        onRowUpdate: (newData, oldData) =>
          new Promise((resolve) => {
            setTimeout(() => {
              resolve();
              if (oldData) {
                setState((prevState) => {
                  const data = [...prevState.data];
                  data[data.indexOf(oldData)] = newData;
                  return { ...prevState, data };
                });
              }
            }, 600);
          }),
        onRowDelete: (oldData) =>
          new Promise((resolve) => {
            setTimeout(() => {
              resolve();
              setState((prevState) => {
                const data = [...prevState.data];
                data.splice(data.indexOf(oldData), 1);
                return { ...prevState, data };
              });
            }, 600);
          }),
      }}
    />
  );
};

DevicesTable.propTypes = {
  handleOnAdd: PropTypes.func.isRequired,
};

export default DevicesTable;
