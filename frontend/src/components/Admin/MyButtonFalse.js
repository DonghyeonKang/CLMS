import React from "react";
import Button from "@mui/material/Button";

const MyButtonFalse = ({ disabled, onClick, boxName }) => {
  return (
    <Button
      disabled={disabled}
      onClick={onClick}
      type="submit"
      fullWidth
      variant="contained"
      color="error"
      sx={{ mt: 3, mb: 2 }}
    >
      {boxName && <span className="box-name">{boxName}</span>}
      거절
    </Button>
  );
};

export default MyButtonFalse;
