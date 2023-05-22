import React, { useState } from "react";
import Container from "@mui/material/Container";
import MyBox from "../components/User/MUI/MyBox";
import MyAvatar from "../components/User/MUI/MyAvatar";
import MyTypography from "../components/User/MUI/MyTypography";
import Grid from "@mui/material/Grid";
import MyButtonFalse from "../components/Admin/MyButtonFalse";
import MyButtonTrue from "../components/Admin/MyButtonTrue";
import MyTable from "../components/Admin/MyTable";

const Admin = () => {
  const [arrIds, setArrIds] = useState([]);
  const handleClick = () => {
    console.log(arrIds);
  };


  return (
    <Container component="main" maxWidth="md">
      <MyBox>
        <MyAvatar />
        <MyTypography>인증요청 목록</MyTypography>
        <MyTable onRowSelectionModelChange={setArrIds} />
        <Grid container justifyContent="space-between">
          <Grid item xs={5}>
            <MyButtonFalse onClick={handleClick} arrIds={arrIds} />
          </Grid>
          <Grid item xs={5}>
            <MyButtonTrue onClick={handleClick} arrIds={arrIds} />
          </Grid>
        </Grid>
      </MyBox>
    </Container>
  );
};

export default Admin;