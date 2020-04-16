INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, remark, is_system, parent_id) VALUES ('menu_type', '菜单类型', 1, 1, '菜单类型', 0, null);
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, remark, is_system, parent_id) VALUES ('menu_type_top', '顶部导航菜单', 1, 2, '顶部导航菜单', 0, last_insert_id());
INSERT INTO lit_dictionary (dict_key, dict_value, order_num, dict_level, remark, is_system, parent_id) VALUES ('menu_type_left', '左侧菜单', 2, 2, '左侧菜单', 0, last_insert_id()-1);
