package com.litong.jfinal;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.paragetter.ParaProcessorBuilder;
import com.jfinal.kit.Kv;
import com.jfinal.server.undertow.UndertowConfig;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.litong.jfinal.getter.KvGetter;
import com.litong.jfinal.handler.ActionSuffixHandler;
import com.litong.jfinal.interceptor.ExceptionInterceptor;
import com.litong.jfinal.route.AdminRoutes;
import com.litong.jfinal.route.ApiRoutes;
import com.litong.jfinal.route.FrontRoutes;
import com.litong.jfinal.route.SystemRoutes;
import com.litong.jfinal.thread.pool.ThreadPoolPlugin;
import com.litong.jfinal.utils.PropKitUtil;
import com.litong.jfinal.utils.UndertowUtil;
import com.litongjava.opencv.utils.OpenCVLibraryUtils;
import com.litongjava.utils.exec.FrpUtils;
import com.litongjava.utils.ip.IpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application extends JFinalConfig {
  private static String configFileName = PropKitUtil.configFileName;

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    // 加载opnecv
    // OpenCVLibraryUtil.init();
    OpenCVLibraryUtils.init();

    // 创建server
    log.info("create server");
    UndertowUtil.server = UndertowServer.create(Application.class, configFileName);
    // 启动Server全局共享
    UndertowUtil.server.addSystemClassPrefix("com.litong.jfinal.utils.UndertowUtil");
    log.info("start server");
    UndertowUtil.server.start();

    info(start);
    // startFrp();
  }

  /**
   * 输出启动信息
   * 
   * @param start
   */
  private static void info(long start) {
    UndertowConfig undertowConfig = UndertowUtil.server.getUndertowConfig();
    int port = undertowConfig.getPort();
    String contextPath = undertowConfig.getContextPath();
    long end = System.currentTimeMillis();
    String[] thisUrl = IpUtils.getThisUrl(port, contextPath);
    for (String string : thisUrl) {
      System.out.println(string);
    }
    System.out.println("http://127.0.0.1:" + port + contextPath);
    System.out.println("启动完成,共使用了" + (end - start) + "ms");
  }

  public void configConstant(Constants me) {
    me.setDevMode(true);
    me.setInjectDependency(true);
    me.setInjectSuperClass(true);
    ParaProcessorBuilder.me.regist(Kv.class, KvGetter.class, null);
    // ParaProcessorBuilder.me.regist(HashMap<String,String>.class,
    // HashMapGetter.class, null);
  }

  public void configRoute(Routes me) {
    me.setMappingSuperClass(true);
    me.add(new FrontRoutes()); // 前端路由
    me.add(new AdminRoutes()); // 后端路由
    me.add(new SystemRoutes()); // 系统路由
    me.add(new ApiRoutes()); // API路由
  }

  @Override
  public void configEngine(Engine me) {
  }

  @Override
  public void configPlugin(Plugins me) {
    // DbConfig.config(me);
    me.add(new ThreadPoolPlugin("pool1", 2, 8, 1024, 10));
  }

  @Override
  public void configInterceptor(Interceptors me) {
    // 1.全局异常拦截器
    me.addGlobalActionInterceptor(new ExceptionInterceptor());
    // Boolean authEnabled = PropKitUtil.getBoolean("project.auth.enable");
    // if (authEnabled) {
    // // 2.登录验证拦截器
    // String[] ignoreUrl = { "/version", "/path", "/image", "/api/users",
    // "/api/role", "/face" };
    // me.add(new AuthInterceptor(ignoreUrl));
    // }

  }

  @Override
  public void configHandler(Handlers me) {
    me.add(new ActionSuffixHandler());
  }

  @Override
  public void onStart() {
    // startCron4jPlugin();
  }

  // private void startCron4jPlugin() {
  // Cron4jPluginService cron4jPluginService =
  // Aop.get(Cron4jPluginService.class);
  // cron4jPluginService.restart();
  // }

  /*
   * 使用onStart会拖慢jfinal启动速度 afterJFinalStart 也会拖慢jfinal启动速度
   */
  public static void startFrp() {
    // 默认会阻塞主线程,所以新开一个线程启动
    FrpUtils frpUtil = new FrpUtils();
    Thread frpClientThread = new Thread(frpUtil, "frp client");
    frpClientThread.run();
  }
}