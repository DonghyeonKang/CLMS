import { BrowserRouter, Routes, Route } from "react-router-dom";
import Main from "./routes/Main";
import Login from "./routes/User/Login"
import Admin from "./routes/Admin";
import SignUp from "./routes/User/SignUp"
import SignUpAd from "./routes/User/SignUpAd"
import FindPw from "./routes/User/FindPw";
import ChangePw from "./routes/User/ChangePw";
import DashBoard from "./routes/Instance/DashBoard";
import InstanceDetail from "./routes/Instance/InstanceDetail";
import CreateInstance from "./routes/Instance/CreateInstance";
import InboundRules from "./routes/Instance/InboundRules";
import ServerResources from "./routes/Server/ServerResources";
import CreateServer from "./routes/Server/CreateServer";



const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" exact element={<Main/>}/>
          <Route path="/Admin" exact element={<Admin/>}/>
          <Route path="/login" exact element={<Login/>}/>
            <Route path="/login/signup" exact element={<SignUp/>}/>
              <Route path="/login/signupad" exact element={<SignUpAd/>}/>
            <Route path="/login/findpw" exact element={<FindPw/>}/>
              <Route path="/login/findpw/ChangePw" exact element={<ChangePw/>}/>
          <Route path="/serverResources" exact element={<ServerResources/>}/>
          <Route path="/createServer" exact element={<CreateServer/>}/>
          <Route path="/dashboard" exact element={<DashBoard/>}/>
            <Route path="/dashboard/:instanceId" exact element={<InstanceDetail/>}/>
              <Route path="/dashboard/:instanceId/inboundRules" exact element={<InboundRules/>}/>

            <Route path="/dashboard/createInstance" exact element={<CreateInstance/>}/>
      </Routes>
    </BrowserRouter>
  );
};

export default Router;