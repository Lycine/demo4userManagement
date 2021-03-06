-- @author hongyu 2017-11-21

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `SALT` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `CELL_PHONE` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `ROLE` int(11) DEFAULT NULL,
  `DEPARTMENT` int(11) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `GMT_CREATE` datetime DEFAULT NULL,
  `GMT_LAST_LOGIN` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

