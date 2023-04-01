import React from "react";
import Container from "@mui/material/Container";
import MyBox from "../components/User/MUI/MyBox";
import MyAvatar from "../components/User/MUI/MyAvatar";
import MyTypography from "../components/User/MUI/MyTypography";
import Grid from "@mui/material/Grid";
import MyButtonTrue from "../components/Admin/MyButtonTrue";
import MyButtonFalse from "../components/Admin/MyButtonFalse";
import MyList from "../components/Admin/MyList";
import data from "../components/Admin/food.json";

const food = data.food;

const Admin = () => {
  return (
    <Container component="main" maxWidth="xs">
      <MyBox>
        <MyAvatar />
        <MyTypography>인증요청 목록</MyTypography>
        <MyList items={food} />
        <Grid container justifyContent="space-between">
          <Grid item xs={5}>
            <MyButtonFalse />
          </Grid>
          <Grid item xs={5}>
            <MyButtonTrue />
          </Grid>
        </Grid>
      </MyBox>
    </Container>
  );
};

export default Admin;
