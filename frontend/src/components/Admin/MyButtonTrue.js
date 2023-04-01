import React from "react";
import Button from "@mui/material/Button";

const MyButtonTrue = ({ disabled, onClick, boxName }) => {
  return (
    <Button
      disabled={disabled}
      onClick={onClick}
      type="submit"
      fullWidth
      variant="contained"
      sx={{ mt: 3, mb: 2 }}
    >
      {boxName && <span className="box-name">{boxName}</span>}
      승인
    </Button>
  );
};

export default MyButtonTrue;
