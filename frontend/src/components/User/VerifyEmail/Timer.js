import React, { useState, useEffect } from 'react';
import MyTypography from '../MUI/MyTypography';

const Timer = () => {
  const [timer, setTimer] = useState(180);

  useEffect(() => {
    const timerId = setTimeout(() => {
      if (timer > 0) {
        setTimer(timer - 1);
      }
    }, 1000);

    return () => clearTimeout(timerId);
  }, [timer]);

  const minutes = Math.floor(timer / 60);
  const seconds = timer % 60;

  return (
    <MyTypography variant="h5.5">
      {`${minutes < 10 ? `0${minutes}` : minutes}:${seconds < 10 ? `0${seconds}` : seconds}`}
    </MyTypography>
  );
};

export default Timer;
