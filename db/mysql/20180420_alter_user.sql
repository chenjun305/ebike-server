ALTER TABLE `user` ADD COLUMN `id_card_num` VARCHAR(64) COMMENT 'ID Card Number' AFTER `gender`;
ALTER TABLE `user` ADD COLUMN `id_card_pics` VARCHAR(512) COMMENT 'ID Card Photos' AFTER `id_card_num`;

