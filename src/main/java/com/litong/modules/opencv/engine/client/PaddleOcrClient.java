package com.litong.modules.opencv.engine.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.json.Json;
import com.jfinal.kit.HttpKit;

public class PaddleOcrClient {
  public static StringBuffer recognize(String requestUrl, String imagePath) throws IOException {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-type", "application/json");
    Map<String, Object> body = getBody(imagePath);

    String data = Json.getJson().toJson(body);
    /*
     * 识别成功返回数据 内容{"result":{"res":["\u56fdG570E9"]}}
     * 
     * 识别识别返回 内容{"result":"not enough values to unpack (expected 3, got 2)"}
     */
    String postResponse = HttpKit.post(requestUrl, data, headers);
    JSONObject responseJsonObject = JSON.parseObject(postResponse);

    return parseResponse(responseJsonObject);
  }

  /**
   * 解析返回数据
   * 
   * @param responseJsonObject
   * @return
   */
  public static StringBuffer parseResponse(JSONObject responseJsonObject) {
    StringBuffer stringBuffer = new StringBuffer();
    try {
      JSONObject result = responseJsonObject.getJSONObject("result");
      JSONArray resArray = result.getJSONArray("res");
      for (int i = 0; i < resArray.size(); i++) {
        stringBuffer.append(resArray.getString(i));
      }
    } catch (Exception e) {
      stringBuffer.append(responseJsonObject.getString("result"));
    }
    return stringBuffer;
  }

  /**
   * 封装请求参数
   * 
   * @param imagePath
   * @return
   * @throws IOException
   */
  public static Map<String, Object> getBody(String imagePath) throws IOException {
    byte[] sourceBytes = FileUtils.readFileToByteArray(new File(imagePath));
    String encodeBytes = Base64.getEncoder().encodeToString(sourceBytes);
    Map<String, String> image = new HashMap<>(1);
    image.put("image", encodeBytes);

    List<Map<String, String>> feed = new ArrayList<>();
    feed.add(image);

    Map<String, List<String>> fetch = new HashMap<>(1);
    List<String> fetchParams = new ArrayList<>();
    fetchParams.add("save_infer_model/scale_0.tmp_1");
    fetch.put("fetch", fetchParams);

    Map<String, Object> body = new HashMap<>();
    body.put("feed", feed);
    body.put("fetch", fetch);
    return body;
  }

}
