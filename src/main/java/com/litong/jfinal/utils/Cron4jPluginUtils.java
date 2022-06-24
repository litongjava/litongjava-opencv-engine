package com.litong.jfinal.utils;

import com.jfinal.plugin.cron4j.Cron4jPlugin;

/**
 * @author litong
 * @date 2020年9月26日_下午9:06:23 
 * @version 1.0 
 * @desc
 */
public class Cron4jPluginUtils {
  private static Cron4jPlugin cron4jPlugin=null;
  
  public static void setPlugin(Cron4jPlugin cron4jPlugin ) {
    Cron4jPluginUtils.cron4jPlugin=cron4jPlugin;
  }
  public static Cron4jPlugin getPlugin() {
    return cron4jPlugin;
  }
}
