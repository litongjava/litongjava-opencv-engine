package com.litong.modules.opencv.engine.services;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.jfinal.kit.Kv;
import com.litongjava.opencv.utils.MatUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpencvService {

  public Kv info() {
    Kv kv = new Kv();
    kv.set("opencv-version", Core.getVersionString());
    return kv;
  }

  public BufferedImage cvtColor(String imagePath, int code) throws FileNotFoundException, IOException {
    Mat dst = new Mat();
    Mat src = MatUtils.imread(imagePath);
    Imgproc.cvtColor(src, dst, code);
//    String baseName = FilenameUtils.getBaseName(imagePath);
//    String dstPath = imagePath.replace(baseName, baseName + "-convert-to-" + code);
//    log.info("dstPath:{}", dstPath);
//    MatUtils.imwrite(dstPath, dst);
    return MatUtils.mat2BufferImage(dst);
  }

}
