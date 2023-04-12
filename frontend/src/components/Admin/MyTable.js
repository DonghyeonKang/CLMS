import { useState, useEffect } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';

export default function MyTable() {
  const [rows, setRows] = useState([]);

  const columns = [
    { field: 'username', headerName: 'username', width: 250 },
    { field: 'university', headerName: 'university', width: 200 },
    { field: 'department', headerName: 'department', width: 200 },
    { field: 'phone', headerName: 'phone', width: 150 },
  ];

  useEffect(() => {
    axios
      .get('http://203.255.3.23:5000/user/manager/verification')
      .then((response) => {
        setRows(response.data.requests);
      })
  }, []);

  return (
    <div style={{ height: 500, width: '100%' }}>
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={5}
        checkboxSelection
        getRowId={(row) => row.username}
      />
    </div>
  );
}
