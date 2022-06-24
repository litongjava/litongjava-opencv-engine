package com.litong.modules.monitoring.db.service;

import java.util.Date;

import com.litong.modules.monitoring.db.common.model.LogMailSend;
import com.litongjava.utils.mail.MailUtils;

/**
 * @author litong
 * @date 2020年9月25日_下午7:17:45 
 * @version 1.0 
 * @desc
 */
public class MailService {

  /**
   * 发送邮件,记录到数据库
   * @param to
   * @param subject
   * @param content
   * @param isDebug
   */
  public void sendMail(String to, String subject, String content, boolean isDebug) {
    MailUtils.sendMail(to, subject, content, isDebug);
    LogMailSend logMailSend = new LogMailSend();
    logMailSend.setTo(to);
    logMailSend.setSubject(subject);
    logMailSend.setContent(content);
    logMailSend.setCreateTime(new Date());
    logMailSend.save();
  }

}
