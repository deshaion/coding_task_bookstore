import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {API_ROOT} from "../apiConfig";

export default function BookDetails() {
    const { id } = useParams();
    const [book, setBook] = useState(null);

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
        }

        onLoad();
    }, [id]);

    return (
        <div className="book-details"></div>
    );
}
