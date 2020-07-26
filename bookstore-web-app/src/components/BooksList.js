import React from 'react';
import {ListGroupItem} from "react-bootstrap";
import {Link} from "react-router-dom";
import "./BooksList.css";

function BooksList(props) {
    return props.books.map((book, i) =>
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
                    <div className="ml-lg-5 order-1 order-lg-2"><Link to={`/task/details/${book.id}`}>More</Link></div>
                </div>
            </ListGroupItem>
        )
    );
}

export default BooksList;