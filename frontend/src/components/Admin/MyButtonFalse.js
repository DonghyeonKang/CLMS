import React from "react";
import Button from "@mui/material/Button";

const MyButtonFalse = ({ onClick, boxName}) => {
  const handleClick = () => {
    const confirmed = window.confirm("정말 거절하시겠습니까?");
    if (confirmed) {
      onClick();
    }
  };

  return (
    <Button
      onClick={handleClick}
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
