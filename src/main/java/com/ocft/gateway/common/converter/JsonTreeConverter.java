package com.ocft.gateway.common.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * @author lijiaxing
 * @Title: JsonTreeConverter
 * @ProjectName gateway
 * @date 2019/12/10上午10:17
 * @Description: json数据可视化转换器
 */
public class JsonTreeConverter {
    public static void main(String[] args) {
        String json = "{\"name\":\"BeJson\",\"url\":\"http://www.bejson.com\",\"page\":88,\"isNonProfit\":true,\"address\":{\"street\":\"科技园路.\",\"city\":\"江苏苏州\",\"country\":\"中国\"},\"links\":[{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"}]}";
        String tree = viewJsonInTree(json);
        System.out.println(tree);
    }


    public static String viewJsonInTree(String content){
        Object jsonObject = JSONObject.parse(content);
        StringBuffer resultJson = new StringBuffer("[");
        StringBuffer processedInputJson = processInputJson(jsonObject,resultJson);
        if(processedInputJson.toString().endsWith(",")){
            processedInputJson = processedInputJson.deleteCharAt(processedInputJson.length() - 1);
        }
        processedInputJson.append("]");
        System.out.println(processedInputJson.toString());
        return processedInputJson.toString();
    }

    public static StringBuffer processInputJson(Object jsonObject,StringBuffer resultJson){
        int countFlag = 1;
        //标志位，用于判断当前json结构是顶层数组结构还是对象嵌套数组的结构
        boolean isArrayFlag = true;
        if(jsonObject instanceof JSONObject){
            //如果是对象中嵌套数组的情况，则将该标志位置为false，该标志位用于处理json数组中的元素还是json数组的情况
            isArrayFlag = false;
            Set<Map.Entry<String, Object>> entries = ((JSONObject) jsonObject).entrySet();
            for(Map.Entry<String, Object> entry:entries){
                if(entry.getValue() instanceof JSONArray){
                    resultJson.append("{\"label\":\""+entry.getKey()+"\",\"children\": [");
                    JSONArray jSONArray = (JSONArray) entry.getValue();
                    for (int i = 0;i<jSONArray.size();i++){
                        resultJson.append("{\"label\":\""+i+"\"");
                        resultJson.append(",\"children\":[");
                        processInputJson(jSONArray.get(i),resultJson);
                        if(resultJson.toString().endsWith(",")){
                            resultJson.deleteCharAt(resultJson.length()-1);
                        }
                        resultJson.append("]},");
                    }
                    if(!resultJson.toString().endsWith("["))
                        resultJson = resultJson.deleteCharAt(resultJson.length()-1);
                    resultJson.append("]},");
                }else if(entry.getValue() instanceof JSONObject){
                    //从数组中取出的元素是对象
                    resultJson.append("{\"label\":\"" + entry.getKey() + "\",\"children\": [");
                    processInputJson(entry.getValue(),resultJson);
                    countFlag++;
                    if(resultJson.toString().endsWith(",")){
                        resultJson.deleteCharAt(resultJson.length()-1);
                    }
                    resultJson.append("]},");
                }else {
                    //如果从数组中取出的元素还是数组，那么接着递归
                    resultJson = resultJson.append("{\"label\":\"" + entry.getKey() + ":" + entry.getValue() + "\"}");
                    if (countFlag < entries.size())
                        resultJson.append(",");
                    countFlag++;

                }
            }
        }else if(jsonObject instanceof JSONArray){
            //当传入的json是json数组时，需要进行判断，即判断当前数组是json的顶层数组还是在json的对象中嵌套的数组。
            if(!isArrayFlag) {
                //对象中嵌套的数组
                JSONArray tempArray = (JSONArray) jsonObject;
                for (int i = 0; i < tempArray.size(); i++) {
                    processInputJson(tempArray.getJSONObject(i), resultJson);
                }
            }else{
                //json顶层就是数组的情况
                JSONArray tempArray = (JSONArray) jsonObject;
                for (int j = 0;j<tempArray.size();j++){
                    resultJson.append("{\"label\":\""+j+"\"");
                    resultJson.append(",\"children\":[");
                    processInputJson(tempArray.get(j),resultJson);
                    if(resultJson.toString().endsWith(",")){
                        resultJson.deleteCharAt(resultJson.length()-1);
                    }
                    resultJson.append("]},");
                }
            }
        }
        return resultJson;
    }
}
