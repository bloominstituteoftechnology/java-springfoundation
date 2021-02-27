import React from "react";
import { Route } from "react-router-dom";
import Login from "./components/Login";
import GetUserInfo from "./components/UserInfo";
import ProtectedRoute from "./components/ProtectedRoute";
import "./App.css";

function App() {
	return (
		<div className="App">
			<Route exact path="/" component={Login} />
			<ProtectedRoute exact path="/userinfo" component={GetUserInfo} />
		</div>
	);
}

export default App;
