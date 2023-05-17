
import React, { useState, useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";
import axios from "axios";

const MyTable = ({ onRowSelectionModelChange }) => {
  const [rows, setRows] = useState([]);

  useEffect(() => {
    axios
      .get("http://203.255.3.23:5000/user/manager/verification")
      .then((response) => {
        setRows(response.data.requests);
      });
  }, []);

  const handleSelectionModelChange = (ids) => {
    onRowSelectionModelChange(ids);
  };

  return (
    <div style={{ height: 500, width: "100%" }}>
      <DataGrid
        rows={rows}
        columns={[
          { field: "username", headerName: "username", width: 250 },
          { field: "university", headerName: "university", width: 200 },
          { field: "department", headerName: "department", width: 200 },
          { field: "phone", headerName: "phone", width: 150 },
        ]}
        getRowId={(row) => row.username}
        checkboxSelection
        onRowSelectionModelChange={handleSelectionModelChange}
      />
    </div>
  );
};

export default MyTable;