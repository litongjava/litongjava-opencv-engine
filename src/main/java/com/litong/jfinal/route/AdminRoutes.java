package com.litong.jfinal.route;

import com.jfinal.config.Routes;
import com.litong.jfinal.controler.UserController;

/**
 * @author bill robot
 * @date 2020年8月16日_下午5:07:49 
 * @version 1.0 
 * @desc
 */
public class AdminRoutes extends Routes {

  @Override
  public void config() {
    add("user", UserController.class);
  }

}
