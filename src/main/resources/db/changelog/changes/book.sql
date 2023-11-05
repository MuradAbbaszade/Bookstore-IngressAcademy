CREATE TABLE `book` (
                        `id` integer AUTO_INCREMENT PRIMARY KEY,
                        `name` varchar(255),
                        `author_id` integer,
                        FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
);