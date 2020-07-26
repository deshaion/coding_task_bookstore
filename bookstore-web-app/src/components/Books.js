import React, {useEffect, useState} from "react";
import {ListGroup, PageHeader} from "react-bootstrap";
import {API_ROOT} from "../apiConfig";
import BooksList from "./BooksList";

export default function Books() {
    const [books, setBooks] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        async function onLoad() {
            try {
                const books = await loadBooks();
                setBooks(books);
            } catch (e) {
                alert(e.message);
            }

            setIsLoading(false);
        }

        onLoad();
    }, []);

    async function loadBooks() {
        let res = [];
        await fetch(`${API_ROOT}/books`)
            .then(res => res.json())
            .then(result => {
                    res = result;
                },
                error => {
                    alert(error.message)
                    setIsLoading(false);
                });
        return res;
    }

    function renderBooksList(books) {
        return <BooksList books={books} />;
    }

    return (
        <div className="Bookstore">
            <PageHeader>Bookstore IT Books</PageHeader>
            <ListGroup>
                {!isLoading && renderBooksList(books)}
            </ListGroup>
        </div>
    );
}
