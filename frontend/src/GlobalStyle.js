import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
  }
  body {
    font-family: "Helvetica", "Arial", sans-serif;
    font-size: 20px;
    color: #545b67;
    background-color: #ffffff;
  }
`;

export default GlobalStyle;