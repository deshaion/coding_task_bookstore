import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {API_ROOT} from "../apiConfig";
import {ListGroup, PageHeader} from "react-bootstrap";
import BooksList from "./BooksList";

export default function BookDetails() {
    const { id } = useParams();
    const [book, setBook] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        async function loadBook() {
            let res = [];
            await fetch(`${API_ROOT}/books/${id}`)
                .then(res => res.json())
                .then(result => {
                        res = result;
                    },
                    error => {
                        alert(error.message)
                    });
            return res;
        }

        async function onLoad() {
            try {
                const book = await loadBook();
                setBook(book);
            } catch (e) {
                alert(e.message);
            }
            setIsLoading(false);
        }

        onLoad();
    }, [id]);

    function renderBook(book) {
        return (
            <div className="book-details">
                <div className="item">
                    <img src={book.image} alt={book.name} width="200" className="book-img"/>
                    <div className="media-body">
                        <h5 className="book-name">{book.name}</h5>
                        <div className="book-price">
                            <h6 className="">{book.price} $</h6>
                        </div>
                        <p className="book-details">{book.details}</p>
                    </div>
                </div>
                {renderSimilar(book.similarBooks)}
            </div>
        );
    }

    function renderSimilar(books) {
        if (books === null || books.length === 0) {
            return "";
        }

        return (
            <div className="similarBooks">
                <PageHeader>Customers were also interested in:</PageHeader>
                <ListGroup>
                    <BooksList books={books} />
                </ListGroup>
            </div>
        );
    }

    return (
        <div className="main">
            {!isLoading && renderBook(book)}
        </div>
    );
}
