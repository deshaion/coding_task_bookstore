# Coding task Bookstore

Java 11 is required.

How to run:
```
$ JAVA_HOME=/path/to/java-11 && ./mvnw spring-boot:run
```

### The task
In this challenge your task is to implement a part of the functionality of a bookstore. We want to test
your java coding skills and your problem-solving abilities within a given time frame.

Please setup a spring boot project from scratch. In order to complete the Task, you have to add
functionality describe in the pages of this document. 

The task consists of 3 pages. Each contains a description of the requirements and a mockup of the
layout. The layout should focus on functionality.

Page 1: Login Screen URL: /task/login

Requirements:
- Validate the input
- Display an error message if the user did not give a password, or e-mail
- Validate the structure of the email
- E-Mails from test.com are invalid
- Write a unit test for the validation of the email
- After login proceed to page “books”
- Don’t use spring-security a simple validation is enough

Page 2: Book list URL: /task/books

Requirements:
- Please display the list of books
- Use the database to retrieve the data for the list
- Create a REST Service to serve the list of books
- Create a Webpage displaying the books
- On “more” navigate to the book detail page

Page 3: Book detail page URL: /task/details

Requirements:
- Implement an Item-Based Collaborative Filtering. Use the cosine-similarity in your computation.
Order the recommended books by their similarity to the selected book.

![alt text](https://github.com/deshaion/coding_task_bookstore/blob/master/images/simFormula.JPG)

- Example similarity of book1 and book2 and book3

| |Book 1|Book 2|Book 3|
| --- |:-------------:|:-----:|:---:|
| User 1 | read | read | |
| User 2 | | read | |
| User 3 | read |read|read|

sim(Book1, Book2) = (1 * 1 + 0 * 1 + 1 * 1) / ((sqrt(1^2 + 0^2 + 1^2) * sqrt(1^2 + 1^2 + 1^2))
sim(Book2, Book3) = (1 * 0 + 1 * 0 + 1 * 1) / ((sqrt(1^2 + 1^2 + 1^2) * sqrt(0^2 + 0^2 + 1^2))
sim(Book1, Book3) = (1 * 0 + 0 * 0 + 1 * 1) / ((sqrt(1^2 + 0^2 + 1^2) * sqrt(0^2 + 0^2 + 1^2))

* Book1 vector is (1 0 1)
* Book2 vector is (1 1 1)
* Book3 vector is (0 0 1)