import React, {useEffect, useState} from "react";
import {ListGroup, ListGroupItem, PageHeader} from "react-bootstrap";
import {API_ROOT} from "../apiConfig";
import {Link} from "react-router-dom";
import "./Books.css";

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

    function renderNotesList(books) {
        return books.map((book, i) =>
            (
                <ListGroupItem>
                    <div className="item">
                        <img src={book.image} alt={book.name} width="200" className="book-img"/>
                        <div className="media-body">
                            <h5 className="book-name">{book.name}</h5>
                            <div className="book-price">
                                <h6 className="">{book.price} $</h6>
                            </div>
                        </div>
                        <div className="ml-lg-5 order-1 order-lg-2"><Link to={`details/${book.id}`}>More</Link></div>
                    </div>
                </ListGroupItem>
            )
        );
    }

    return (
        <div className="Bookstore">
            <PageHeader>Bookstore IT Books</PageHeader>
            <ListGroup>
                {!isLoading && renderNotesList(books)}
            </ListGroup>
        </div>
    );
}
