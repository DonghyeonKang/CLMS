import React from "react";

const EnterKeyListener = ({ callback, notAllow }) => {
  const onCheckEnter = (e) => {
    if (e.key === "Enter" && !notAllow) {
      callback();
    }
  };

  return <div onKeyDown={onCheckEnter} />;
};

export default EnterKeyListener;
