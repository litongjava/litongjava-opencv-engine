package com.litong.modules.web.tools.data.ro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litong
 * @date 2021年3月3日_下午6:22:11 
 * @version 1.0 
 * @desc 搜索的结果对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbSearchRo {
  private String tableName,columnName,value;
}
