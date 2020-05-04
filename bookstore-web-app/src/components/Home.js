import logo from "../logo.svg";
import {Link} from "react-router-dom";
import React from "react";

function Home() {
    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <Link to="/task/login">Login</Link>
            </header>
        </div>
    );
}

export default Home;