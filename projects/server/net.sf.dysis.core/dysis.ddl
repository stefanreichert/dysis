DROP TABLE IF EXISTS `dysis`.`activity`;
DROP TABLE IF EXISTS `dysis`.`person`;
DROP TABLE IF EXISTS `dysis`.`person_activity`;
DROP TABLE IF EXISTS `dysis`.`project`;
DROP TABLE IF EXISTS `dysis`.`session`;
DROP TABLE IF EXISTS `dysis`.`timeentry`;

CREATE TABLE  `dysis`.`activity` (
  `id` bigint(20) NOT NULL auto_increment,
  `active` bit(1) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `projectReference_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FKA126572FE8C182A1` (`projectReference_id`),
  CONSTRAINT `FKA126572FE8C182A1` FOREIGN KEY (`projectReference_id`) REFERENCES `project` (`id`)
);

CREATE TABLE  `dysis`.`person` (
  `id` bigint(20) NOT NULL auto_increment,
  `active` bit(1) NOT NULL,
  `employmentDate` datetime NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `weekHours` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `userId` (`userId`)
);

CREATE TABLE  `dysis`.`person_activity` (
  `members_id` bigint(20) NOT NULL,
  `activities_id` bigint(20) NOT NULL,
  KEY `FK97B94B9539C2E01` (`members_id`),
  KEY `FK97B94B9FC6032DC` (`activities_id`),
  CONSTRAINT `FK97B94B9FC6032DC` FOREIGN KEY (`activities_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `FK97B94B9539C2E01` FOREIGN KEY (`members_id`) REFERENCES `person` (`id`)
);

CREATE TABLE  `dysis`.`project` (
  `id` bigint(20) NOT NULL auto_increment,
  `description` varchar(255) NOT NULL,
  `endDate` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `startDate` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`)
);

CREATE TABLE  `dysis`.`session` (
  `id` bigint(20) NOT NULL auto_increment,
  `lastAccess` datetime NOT NULL,
  `sessionId` varchar(255) NOT NULL,
  `userId` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
);

CREATE TABLE  `dysis`.`timeentry` (
  `id` bigint(20) NOT NULL auto_increment,
  `date` datetime NOT NULL,
  `hours` int(11) NOT NULL,
  `activity_id` bigint(20) NOT NULL,
  `person_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK78309D85D6596245` (`person_id`),
  KEY `FK78309D851E6F907A` (`activity_id`),
  CONSTRAINT `FK78309D851E6F907A` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `FK78309D85D6596245` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
);

INSERT INTO `dysis`.`person` (`active`, `employmentDate`, `firstname`, `lastname`, `password`, `userId`, `weekHours`) VALUES (1,'2009-05-01 16:41:37','Kuddel','Woehr','dysis','kwoehr',40);
