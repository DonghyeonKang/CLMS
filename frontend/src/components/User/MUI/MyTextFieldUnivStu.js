import React, { useEffect, useState } from 'react';
import { TextField, MenuItem } from '@mui/material';
import axios from 'axios';
import {baseUrl} from "../../../Atoms"
import { useRecoilState } from "recoil";

const MyTextFieldUnivStu = (props) => {
  const [BASEURL,] = useRecoilState(baseUrl);
  const [universities, setUniversities] = useState([]);

  useEffect(() => {
    // 대학교 정보를 가져오는 API 요청
    axios.get(BASEURL+ '/register/universities')
      .then(response => {
        setUniversities(response.data.universities);
      })
      .catch(error => {
        console.error(error);
      });
  }, [BASEURL]);

  return (
    <TextField
      required
      select
      fullWidth
      {...props}
      margin="normal"
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
