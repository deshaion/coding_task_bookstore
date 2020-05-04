import React, {useState} from "react";
import {useHistory} from "react-router-dom";
import {Alert, ControlLabel, FormControl, FormGroup} from "react-bootstrap";
import "./Login.css";
import LoaderButton from "./LoaderButton";
import {API_ROOT} from "../apiConfig";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [styleDivError, setStyleDivError] = useState("none");

    const history = useHistory();

    async function handleSubmit(event) {
        event.preventDefault();

        setIsLoading(true);

        await fetch(`${API_ROOT}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({'email': email, 'password': password})
        })
            .then(res => res.json())
            .then(result => {
                    console.log(result);

                    if (result.status === "success") {
                        history.push("/task/books");
                    } else {
                        setErrorMessage(result.message);
                        setStyleDivError("block");
                        setIsLoading(false);
                    }
                },
                error => {
                    alert(error.message)
                    setIsLoading(false);
                });
    }

    return (
        <div className="Login">
            <form onSubmit={handleSubmit}>
                <FormGroup controlId="email" bsSize="large">
                    <ControlLabel>Email</ControlLabel>
                    <FormControl
                        autoFocus
                        type="email"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                    />
                </FormGroup>
                <FormGroup controlId="password" bsSize="large">
                    <ControlLabel>Password</ControlLabel>
                    <FormControl
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        type="password"
                    />
                </FormGroup>
                <LoaderButton block bsSize="large" type="submit" isLoading={isLoading}>
                    Login
                </LoaderButton>
                <div style={{margin: "10px 0", display: styleDivError}}>
                    <Alert variant="warning">
                        {errorMessage}
                    </Alert>
                </div>
            </form>
        </div>
    );
}
