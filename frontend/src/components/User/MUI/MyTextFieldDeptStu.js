import React, { useEffect, useState } from 'react';
import { TextField, MenuItem } from '@mui/material';
import axios from 'axios';
import {baseUrl} from "../../../Atoms"
import { useRecoilState } from "recoil";

const MyTextFieldDeptStu = (props) => {
  const [BASEURL,] = useRecoilState(baseUrl);
  const [departments, setDepartments] = useState([]);
  const [universityId, setUniversityId] = useState(1);

  useEffect(() => {
    const fetchDepartments = async () => {
      try {
        const response = await axios.get(BASEURL+'/register/departments'+{
          params: {
            universityId: universityId
          }
        });
        setDepartments(response.data.departments);
      } catch (error) {
        console.error(error);
      }
    };

    fetchDepartments();
  }, [universityId, BASEURL]); 

  const handleUniversityChange = (event) => {
    setUniversityId(event.target.value); 
  };

  return (
    <TextField
      required
      select
      fullWidth
      value={universityId} 
      onChange={handleUniversityChange} 
      {...props}
      margin="normal"
    >
      {departments.map((department) => (
        <MenuItem key={department.id} value={department.name}>
          {department.name}
        </MenuItem>
      ))}
    </TextField>
  );
}

export default MyTextFieldDeptStu;
