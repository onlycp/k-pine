CREATE TABLE `dev_seats` (
     `id` varchar(100) NOT NULL,
     `node_id` varchar(100) DEFAULT NULL,
     `num` varchar(50) DEFAULT NULL,
     `type` varchar(50) DEFAULT NULL,
     `status` varchar(50) DEFAULT NULL,
     `user_id` varchar(50) DEFAULT NULL,
     `floor_id` varchar(50) DEFAULT NULL,
     `description` varchar(200) DEFAULT NULL,
     `when_created` varchar(100) DEFAULT NULL,
     `when_modified` varchar(100) DEFAULT NULL,
     `who_created` varchar(255) DEFAULT NULL,
     `who_modified` varchar(255) DEFAULT NULL,
     PRIMARY KEY (`id`)
);