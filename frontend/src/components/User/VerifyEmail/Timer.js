import React, { useState, useEffect } from 'react';
import MyTypography from '../MUI/MyTypography';

const Timer = ({ onTimerExpired, timerRunning }) => {
  const [timer, setTimer] = useState(180);
  const [timerExpired, setTimerExpired] = useState(false);

  useEffect(() => {
    let timerId = null;

    if (timerRunning) {
      timerId = setTimeout(() => {
        if (timer > 0) {
          setTimer(timer - 1);
        } else {
          setTimerExpired(true);
          onTimerExpired();
        }
      }, 1000);
    }

    return () => {
      clearTimeout(timerId);
    };
  }, [timer, timerRunning, onTimerExpired]);

  const minutes = Math.floor(timer / 60);
  const seconds = timer % 60;

  if (timerExpired) {
    return (
      <MyTypography variant="h5.5">
        인증 시간 초과!
      </MyTypography>
    );
  }

  return (
    <MyTypography variant="h5.5">
      {`${minutes < 10 ? `0${minutes}` : minutes}:${seconds < 10 ? `0${seconds}` : seconds}`}
    </MyTypography>
  );
};

export default Timer;
