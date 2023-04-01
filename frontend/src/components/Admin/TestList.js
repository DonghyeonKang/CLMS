import React from "react";
import foodList from "./food";

function FoodList() {
  return (
    <div>
      <h2>음식 목록</h2>
      <ul>
        {foodList.map((food, index) => (
          <li key={index}>{food}</li>
        ))}
      </ul>
    </div>
  );
}

export default FoodList;
