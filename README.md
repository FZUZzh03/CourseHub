
show databases ;
create database CourseHub;
use CourseHub;
CREATE TABLE `user`(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE ,
    `password` VARCHAR(100) NOT NULL ,
    `name` VARCHAR(50) NOT NULL ,
    `role` VARCHAR(10) NOT NULL ,
    `age` INT ,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT  CURRENT_TIMESTAMP,
    `is_deleted` TINYINT DEFAULT 0
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `course`(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `course_name` VARCHAR(100) NOT NULL ,
    `teacher_id` BIGINT NOT NULL ,
    `max_num` INT NOT NULL ,
    `selected_num` INT DEFAULT 0,
    `classroom` VARCHAR(50) NOT NULL ,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT  CURRENT_TIMESTAMP,
    `is_deleted` TINYINT DEFAULT 0,
     FOREIGN KEY (`teacher_id`) REFERENCES `user`(`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `lesson`(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `student_id` BIGINT NOT NULL ,
    `course_id` BIGINT NOT NULL ,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT  CURRENT_TIMESTAMP,
    `is_deleted` TINYINT DEFAULT 0,
     FOREIGN KEY (student_id) REFERENCES `user`(`id`),
     FOREIGN KEY (`course_id`) REFERENCES `course`(`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;<img width="856" height="1576" alt="image" src="https://github.com/user-attachments/assets/faa60d83-09c6-4af0-ba67-731ba6aa6944" />

