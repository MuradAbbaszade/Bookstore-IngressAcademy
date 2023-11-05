CREATE TABLE `author` (
                          `id` integer AUTO_INCREMENT PRIMARY KEY,
                          `name` varchar(255),
                          `age` integer,
                          `user_id` integer,
                          FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);
