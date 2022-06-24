package com.litong.jfinal.utils;

import org.junit.Test;

import com.jfinal.plugin.cron4j.Cron4jPlugin;

import it.sauronsoftware.cron4j.SchedulingPattern;

/**
 * @author litong
 * @date 2020年9月26日_下午7:31:12 
 * @version 1.0 
 * @desc
 */
public class Cron4jPluginUtilsTest {

  @Test
  public void test() {
    Cron4jPlugin cron4jPlugin = new Cron4jPlugin();
    cron4jPlugin.addTask("*/*", () -> {
      System.out.println("hello cron4j");
    });
    try {
      cron4jPlugin.start();
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Test
  public void test2() {
    try {
      new SchedulingPattern("*/*");
    }catch(Exception e) {
      e.printStackTrace();
    }
    
  }
}
