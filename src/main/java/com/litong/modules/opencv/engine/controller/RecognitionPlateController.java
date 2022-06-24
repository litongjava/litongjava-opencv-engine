package com.litong.modules.opencv.engine.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.core.paragetter.Para;
import com.litong.modules.opencv.engine.services.RecognitionPlateUtils;
import com.litongjava.opencv.model.DebugInfo;
import com.litongjava.opencv.model.Plate;
import com.litongjava.utils.vo.JsonBean;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;

@Path("recognition/plate")
@Slf4j
public class RecognitionPlateController extends Controller {

  @Inject
  private RecognitionPlateUtils recognitionPlateService;

  /**
   * 识别车牌 1.调用其他接口上传文件 2.调用此接口识别上传文件 3.返回识别结果
   * 
   * @param filename
   * @throws IOException 
   * @throws FileNotFoundException 
   * @throws TesseractException 
   */
  public void index(@Para("") DebugInfo recognize) throws FileNotFoundException, IOException, TesseractException {
    log.info("recongnize:{}",recognize);
    Plate plate = recognitionPlateService.index(recognize);
    JsonBean<Plate> jsonBean = new JsonBean<>(plate);
    renderJson(jsonBean);
  }
}
