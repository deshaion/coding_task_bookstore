import React from "react";
import {Route, Switch} from "react-router-dom";
import NotFound from "./components/NotFound";
import Login from "./components/Login";
import Home from "./components/Home";

export default function Routes() {
    return (
        <Switch>
            <Route exact path="/">
                <Home />
            </Route>
            <Route exact path="/task/login">
                <Login />
            </Route>

            {/* Finally, catch all unmatched routes */}
            <Route>
                <NotFound />
            </Route>
        </Switch>
    );
}
