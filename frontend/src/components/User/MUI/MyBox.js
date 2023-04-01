import React from "react";
import Box from '@mui/material/Box';

const MyBox = (props) => {
  return (
    <Box
      sx={{
        marginTop: 8,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      }}
      {...props}
    />
  );
}

export default MyBox;
