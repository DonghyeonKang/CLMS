import React from "react";
import Button from "@mui/material/Button";

const MyButton = ({ disabled, onClick, children }) => {
  return (
    <Button
      disabled={disabled}
      onClick={onClick}
      type="submit"
      fullWidth
      variant="contained"
      sx={{ mt: 3, mb: 2 }}>
      {children}
    </Button>
  );
};

export default MyButton;
