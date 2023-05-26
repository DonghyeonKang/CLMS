import React, { useEffect, useState } from 'react';
import { TextField, MenuItem } from '@mui/material';
import axios from 'axios';
import { baseUrl } from "../../../Atoms";
import { useRecoilState } from "recoil";

const MyTextFieldUnivStu = ({ setUnivStu, ...props }) => {
  const [BASEURL,] = useRecoilState(baseUrl);
  const [universities, setUniversities] = useState([]);
  
  useEffect(() => {
    axios.get(BASEURL + '/register/universities')
      .then(response => {
        setUniversities(response.data.universities);
      })
      .catch(error => {
        console.error(error);
      });
  }, [BASEURL]);

  const handleUniversityChange = (event) => {
    setUnivStu(event.target.value);
};

  return (
    <TextField
      required
      select
      fullWidth
      {...props}
      margin="normal"
      onChange={handleUniversityChange} 
    >
      {universities.map((university) => (
        <MenuItem key={university.id} value={university.name}>
          {university.name}
        </MenuItem>
      ))}
    </TextField>
  );
}

export default MyTextFieldUnivStu;
