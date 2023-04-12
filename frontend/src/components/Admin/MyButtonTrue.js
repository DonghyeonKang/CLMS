import React from "react";
import Button from "@mui/material/Button";

const MyButtonTrue = ({ onClick, boxName, checked }) => {
  const handleClick = () => {
    const confirmed = window.confirm("정말 승인하시겠습니까?");
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
      sx={{ mt: 3, mb: 2 }}
    >
      {boxName && <span className="box-name">{boxName}</span>}
      승인
    </Button>
  );
};

export default MyButtonTrue;
