package com.litong.jfinal.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

//@Slf4j
public class ActionSuffixHandler extends Handler {

  private List<String> suffixList = new ArrayList<>();

  public ActionSuffixHandler() {
    suffixList.add(".action");
    suffixList.add(".php");
  }

  public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
    for (String suffix : suffixList) {
      int lastIndexOf = target.lastIndexOf(suffix);
      if (target.lastIndexOf(suffix) > -1) {
        target = target.substring(0, lastIndexOf);
      }
    }
    next.handle(target, request, response, isHandled);
  }
}