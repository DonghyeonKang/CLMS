import React, { useState } from 'react';
import Container from '@mui/material/Container';
import MyBox from '../components/User/MUI/MyBox';
import MyAvatar from '../components/User/MUI/MyAvatar';
import MyTypography from '../components/User/MUI/MyTypography';
import Grid from '@mui/material/Grid';
import MyButtonTrue from '../components/Admin/MyButtonTrue';
import MyButtonFalse from '../components/Admin/MyButtonFalse';
import MyList from '../components/Admin/MyList';
import data from '../components/Admin/food.json';

const Admin = () => {
  const [food, setFood] = useState(data.food);
  const [checked, setChecked] = useState([]);

  const handleToggle = (index) => () => {
    const currentIndex = checked.indexOf(index);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(index);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };

  const handleDelete = () => {
    const newList = food.filter((item, index) => !checked.includes(index));
    setFood(newList); // food state 값을 업데이트
    setChecked([]); // checked state 값을 초기화
  };

  return (
    <Container component="main" maxWidth="xs">
      <MyBox>
        <MyAvatar />
        <MyTypography>인증요청 목록</MyTypography>
        <MyList items={food} checked={checked} handleToggle={handleToggle} onDelete={handleDelete} />
        <Grid container justifyContent="space-between">
          <Grid item xs={5}>
            <MyButtonFalse onClick={handleDelete} checked={checked} />
          </Grid>
          <Grid item xs={5}>
            <MyButtonTrue onClick={handleDelete} checked={checked} />
          </Grid>
        </Grid>
      </MyBox>
    </Container>
  );
};

export default Admin;
