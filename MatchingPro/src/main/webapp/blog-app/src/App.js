import React, { useState } from 'react';
import { Route } from 'react-router-dom';
import LoginForm from './pages/user/LoginForm';
import Header from './components/Header';
import MainForm from './pages/MainForm';
import JoinForm from './pages/user/JoinForm';
import Team_create from './pages/team/Team_create';
import Logout from './pages/user/Logout';
import Team_detail from './pages/team/Team_detail';
import MyTeam from './pages/team/MyTeam';
import Background from './components/Background';

const App = () => {


  // 페이지가 로딩 되면 localStorage에서 token을 check해서 true or false를 return해준다
  const tokenCheck = () => {
    console.log("App.js:: display Authorization - 로그인 여부 확인을 위한 token check", localStorage.getItem("Authorization"));
    if (localStorage.getItem("Authorization") != null) return true;
    else return false;
  }

  // 토큰이 있다(1) or 없다(0) => 있다 = 로그인 o, 없다 = 로그인 x 
  const [isToken, setIsToken] = useState(tokenCheck());

  // 로그인 하면 실행할거야. 0->1로 바꿔주는 함수. 
  const setToken = () => {
    if (isToken) setIsToken(0);  // 1이면 0으로 바꾸고
    else setIsToken(1);         // 0이면 1로 바꾸고
  }

  return (
    <div>
      <Header isToken={isToken} setIsToken={setIsToken}></Header>
      <Background></Background>
      
      {/* 아래는 Router */}
      <Route path="/" exact={true} component={MainForm}></Route>
      <Route path="/Join" exact={true} component={JoinForm}></Route>
      {/* <Route path="/Login" exact={true} component={LoginForm}><LoginForm setToken={setToken}></LoginForm></Route> */}
      <Route path="/Logout" exact={true} component={Logout}><Logout setToken={setToken}></Logout></Route>

      <Route path="/Team_detail/:id" exact={true} component={Team_detail}></Route>
      <Route path="/Team_create" exact={true} component={Team_create}></Route>
      <Route path="/MyTeam" exact={true} component={MyTeam}></Route>
      
      {/* path에 적은 주소로 요 이 들어오면 component를 return해준다 */}
      {/* link to - /MyTeam -> component={MyTeam} */}
    </div>
  );
};

export default App;