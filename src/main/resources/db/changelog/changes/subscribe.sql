CREATE TABLE `subscribe` (
                                `student_id` integer,
                                `author_id` integer,
                                FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
                                FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
);