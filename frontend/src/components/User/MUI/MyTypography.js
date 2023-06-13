import React from "react";
import Typography from '@mui/material/Typography';

const MyTypography = ({ component, variant, children }) => {
  return (
    <Typography component={component} variant={variant}>
      {children}
    </Typography>
  );
};

export default MyTypography;
