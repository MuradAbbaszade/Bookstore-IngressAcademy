CREATE TABLE `student_book` (
                                `student_id` integer,
                                `book_id` integer,
                                FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
                                FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
);
