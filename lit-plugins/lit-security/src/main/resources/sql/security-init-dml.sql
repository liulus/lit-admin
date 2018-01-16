INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('authority_type', '权限类型', 4, 1, '权限类型', 1, null);
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('system_authority', '系统权限', 1, 2, '系统权限', 1, last_insert_id());
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, memo, is_system, parent_id) VALUES ('other_authority', '其他权限', 2, 2, '其他权限', 0, last_insert_id()-1);
