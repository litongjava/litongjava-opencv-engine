package com.litong.jfinal.utils;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

/**
 * @author bill robot
 * @date 2020年9月6日_上午9:16:35 
 * @version 1.0 
 * @desc
 */
public class TableMappingUtils {

  public static String replaceTableName(String sql, @SuppressWarnings("rawtypes") Class<? extends Model> clazz) {
    Table table = TableMapping.me().getTable(clazz);
    return String.format(sql, table.getName());
  }
}
