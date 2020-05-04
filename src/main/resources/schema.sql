CREATE TABLE IF NOT EXISTS `book` (
    `id` varchar(20) NOT NULL,
    `name` varchar(200) NOT NULL,
    `details` varchar(2000),
    `price` DECIMAL(10, 3),
    `image` varchar(400),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `books_views` (
    `book_id` varchar(20) NOT NULL,
    `user` varchar(200) NOT NULL
);