import React from "react";
import Button from "@mui/material/Button";

const MyButtonFalse = ({ onClick, boxName, arrIds }) => {
  const handleClick = () => {
    const confirmed = window.confirm("정말 거절하시겠습니까?");
    if (confirmed) {
      if (arrIds && arrIds.length > 0) {
        onClick();
      } else {
        alert("선택된 데이터가 없습니다!");
      }
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
