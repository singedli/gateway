package com.ocft.gateway.common.evaluator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ocft.gateway.common.parser.JsonSlimParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author lijiaxing
 * @Title: JsonSlimEvalutor
 * @ProjectName gateway
 * @date 2019/11/23下午5:05
 * @Description: json 剪裁处理程序
 *
 *
 */
public class JsonSlimEvalutor {
    private static final String INNER_SPLIT_FLAG = ",";
    /**
     *
     * @param t 要剪裁的json对象，可以是JSONObject，也可以是JSONArray
     * @param paths 要剪裁的key的路径
     *
     *      例如json字符串为：
     *              {
     *                  "name": "BeJson",
     *                  "url": "http://www.bejson.com",
     *                  "page": 88,
     *                  "address": {
     *                      "street": "科技园路.",
     *                      "city": "江苏苏州",
     *                      "country": "中国"
     *                  },
     *                  "links": [
     *                      {
     *                          "name": "Google",
     *                          "url": "http://www.google.com"
     *                      },
     *                      {
     *                          "name": "Baidu",
     *                          "url": "http://www.baidu.com"
     *                      }
     *                  ]
     *              }
     *      1、删除单个属性：例如要将json中的url字段删除掉，则paths为"url"
     *         删除多个属性：例如要将json中的url和page字段同事删除掉，则paths为"url,page"
     *      2、删除json对象中的对象：例如要将json中的address对象的city字段删除掉，则paths为"address.city"
     *      3、删除json对象中的数组：例如要将json中的links数组中每一个对象的name字段删除掉，则path为"links.name"
     *
     * @return
     */

    public static void main(String[] args) {
        String json = "{\"https://portal-test.medsci.cn/portal-medscimeeting/recommendMeeting\":{\"data\":[{\"city\":\"上海\",\"name\":\"Mayo Clinic医疗模式国际研讨会\",\"startTime\":\"2019-08-13\",\"id\":3},{\"city\":\"上海\",\"name\":\"药物警戒培训班\",\"startTime\":\"2019-08-13\",\"id\":4},{\"city\":\"上海\",\"name\":\"微流控大会\",\"startTime\":\"2019-08-13\",\"id\":5},{\"city\":\"上海\",\"name\":\"临床质谱与高端医学检验发展论坛\",\"startTime\":\"2019-08-13\",\"id\":6},{\"city\":\"北京\",\"name\":\"液体活检产业发展论坛\",\"startTime\":\"2019-08-02\",\"id\":7}],\"message\":\"成功\",\"status\":200},\"https://portal-test.medsci.cn/top/weekTop\":{\"data\":[{\"cover\":\"http://refactor.test.medsci.cn/20191118/b2c3ea7fe32d406baa68502c07f756a6/118_guideindexpic.jpg\",\"module\":\"guider\",\"moduleId\":10489,\"title\":\"2019 AHA科学声明：急性肺栓塞介入治疗—当前状况和新证据发展\"},{\"cover\":\"http://refactor.test.medsci.cn/20191112/0ac579b091a24cafaa6d3fbfd81d4f54/20191108024215209_jpg_h160.jpg\",\"module\":\"article\",\"moduleId\":178256,\"title\":\"感染，免疫检查点抑制剂应用的一个难点\"},{\"cover\":\"http://refactor.test.medsci.cn/20191015/b7f3bf271e904c9f96374283dc5f50ab/20191014151336179.png\",\"module\":\"article\",\"moduleId\":178241,\"title\":\"原发性肺肉瘤样癌的CT表现与鉴别诊断\"},{\"cover\":\"http://refactor.test.medsci.cn/20191015/d94a9d2b79a242b1846434632535c984/20191013205730505.jpg\",\"module\":\"article\",\"moduleId\":178242,\"title\":\"【ESMO速递】晚期食管鳞癌最新研究进展，免疫治疗再下一城\"},{\"cover\":\"http://refactor.test.medsci.cn/20191029/9c6ecbf41880452b8895bf60cd825bd1/测试12.jpeg\",\"module\":\"article\",\"moduleId\":178251,\"title\":\"全谷物饮食会影响你的肠道微生物吗？\"},{\"cover\":\"http://refactor.test.medsci.cn/20191030/b83958db69b240bb9f6bcb3b2263050e/屏幕快照 2019-10-30 10.00.34.png\",\"module\":\"article\",\"moduleId\":178254,\"title\":\"抑制胶质瘤细胞增殖与浸润\"},{\"cover\":\"http://refactor.test.medsci.cn/20191118/313ca5b80a3c4379a54ac9b72c67f368/20160801093740788.png\",\"module\":\"guider\",\"moduleId\":10487,\"title\":\"2019 亚洲专家组共识建议：动态血压监测\"},{\"cover\":\"http://refactor.test.medsci.cn/20191024/5d692293ed6a483caacc7f6f1202b0a0/20191012191319604.jpg\",\"module\":\"article\",\"moduleId\":178247,\"title\":\"肺癌靶向治疗的现状和发展趋势——精细、联合、克服耐药和脑转移\"},{\"cover\":\"http://news.medlive.cn/uploadfile/ueditor/php/upload/image/20191028/1572252792613401.jpeg\",\"module\":\"article\",\"moduleId\":178255,\"title\":\"9位医生含泪说出的压箱底的救赎故事……\"},{\"cover\":\"http://refactor.test.medsci.cn/20191017/0b36fde72bac4380a82c85aabc9f43c6/20160801093740788.png\",\"module\":\"guider\",\"moduleId\":10486,\"title\":\"2019 AHA科学声明：症状性外周动脉疾病患者监督运动疗法的实施 PLOS ONE\"}],\"message\":\"成功\",\"status\":200}}";
        String path = "https://portal-test.medsci.cn/portal-medscimeeting/recommendMeeting.data.city";
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject remove = remove(jsonObject, path);
        System.out.println(JSONObject.toJSONString(remove,SerializerFeature.PrettyFormat));
    }

    public static <T extends JSON> T remove(T t, String paths){
        String[] split = paths.split(INNER_SPLIT_FLAG);
        for (String path : split) {
            deleteJsonProperty(t, path);
        }
        return t;
    }

    public static <T extends JSON> T retain(T t, String paths){
        Map<String, Object> pathMap = JsonSlimParser.pathStr2Map(paths);
        TreeSet<String> resultSet = new TreeSet<>();
        JsonSlimParser.getKeepStylePath("",pathMap,resultSet);
        keepKeysByGivenKeySet(resultSet,t);
        return t;
    }

    /**
     * 递归删除指定字段
     *
     * @param outterObject
     * @param source
     * @return
     */
    private static void deleteJsonProperty(Object outterObject, String source) {
        if (outterObject == null || source == null || "".equals(source))
            return;
        if (outterObject instanceof JSONObject) {
            JSONObject tempJson = (JSONObject) outterObject;
            if (source.contains(".")) {
                String correctKey = findCorrectKey(tempJson, source);
                deleteJsonProperty(tempJson.get(correctKey), source.substring(correctKey.length()+1));
                return;
            }
            tempJson.remove(source);
        } else if (outterObject instanceof JSONArray) {
            JSONArray tempArray = (JSONArray) outterObject;
            for (int i = 0; i < tempArray.size(); i++) {
                if (tempArray.get(i) instanceof JSONArray) {
                    JSONArray tempJson = (JSONArray) tempArray.get(i);
                    deleteJsonProperty(tempJson, source);
                } else {
                    JSONObject tempJson = (JSONObject) tempArray.get(i);
                    if (source.contains(".")) {
                        String correctKey = findCorrectKey(tempJson, source);
                        if (tempJson.get(correctKey) == null)
                            continue;
                        deleteJsonProperty(tempJson.get(correctKey), source.substring(correctKey.length()+1));
                    } else {
                        tempJson.remove(source);
                    }
                }
            }
        }
    }


    /**
     * 递归实现字段保留功能
     *
     * @param outterObject
     * @param source
     */
    private static void retainJsonProperty(Object outterObject, String source) {
        if (outterObject == null || source == null || "".equals(source))
            return;

        if (outterObject instanceof JSONObject) {
            JSONObject obj = ((JSONObject) outterObject);
            if (source.contains(".")) {
                String arg = source.substring(0, source.indexOf('.'));
                retainJsonProperty(obj.get(arg), source.substring(source.indexOf('.') + 1));
            } else
                JsonSlimParser.keepJsonObjectByGivenKeys(obj, source);
        } else if (outterObject instanceof JSONArray) {
            JSONArray tempArray = (JSONArray) outterObject;
            for (int i = 0; i < tempArray.size(); i++) {
                if (tempArray.get(i) instanceof JSONObject) {
                    JSONObject obj = tempArray.getJSONObject(i);
                    if (source.contains(".")) {
                        String arg = source.substring(0, source.indexOf('.'));
                        retainJsonProperty(obj.get(arg), source.substring(source.indexOf('.') + 1));
                    } else {
                        JsonSlimParser.keepJsonObjectByGivenKeys(obj, source);
                    }
                } else {
                    JSONArray jsonArray = (JSONArray) tempArray.get(i);
                    retainJsonProperty(jsonArray, source);
                }
            }
        }
    }

    public static void keepKeysByGivenKeySet(TreeSet<String> keySet, Object obj){
        if(!(keySet instanceof TreeSet))
            return;
        for(String key:keySet){
            retainJsonProperty(obj,key);
        }
    }

    private static String findCorrectKey(JSONObject jsonObject,String path){
        String key = null;
        int i = 1;
        while(!jsonObject.containsKey(key)){
            int index = StringUtils.ordinalIndexOf(path,".", i++);
            key = path.substring(0,index);
        }
        return key;
    }
}
