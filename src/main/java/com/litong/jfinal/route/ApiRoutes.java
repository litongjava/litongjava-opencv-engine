package com.litong.jfinal.route;

import com.jfinal.config.Routes;
import com.litong.jfinal.controler.ApiFormController;
import com.litong.jfinal.controler.ApiRoleController;
import com.litong.jfinal.controler.ApiUsersController;
import com.litong.jfinal.controler.ImageController;
import com.litong.modules.monitoring.db.controller.DbController;
import com.litong.modules.web.tools.controller.DbSearchController;

/**
 * @author bill robot
 * @date 2020年8月16日_下午5:08:54
 * @version 1.0
 * @desc
 */
public class ApiRoutes extends Routes {

  @Override
  public void config() {
    add("api/form", ApiFormController.class);
    add("api/users", ApiUsersController.class);
    add("api/role", ApiRoleController.class);
    add("image", ImageController.class);
    add("db", DbController.class);
    add("db/search", DbSearchController.class);
    //add("api/opencv",ApiOpenCvController.class);
    this.scan("com.litong.modules.opencv.engine.controller.");
  }
}
