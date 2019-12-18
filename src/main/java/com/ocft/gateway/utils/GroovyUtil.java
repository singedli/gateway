package com.ocft.gateway.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: Bobby
 * @create: 2019-12-16 18:00
 * @description: groovy脚本调用工具类
 **/
public class GroovyUtil {

    static ScriptEngine engine;  //获取groovy编译类

    static {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("groovy");
    }

    public static Object execute(String scriptText,Map context) throws Exception {
        // 这个地方需要使用缓存，达到编译一次，多次执行。
        Binding binding = new Binding();

        Set<?> its = context.entrySet();
        //上下文参数
        for (Object o : its) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) o;
            binding.setProperty(entry.getKey()+"", entry.getValue());
        }
        GroovyShell shell = new GroovyShell(binding);
        Object o = shell.evaluate(scriptText);
        return o;
    }


    public static void main(String[] args) throws Exception {
        String scriptText = " System.out.println(' Hello World! I am ' + name);";
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("name","Bobby");

        String scriptText1 = "Integer i =  new Integer(3);return i";
        Object o = execute(scriptText1, context);
        System.out.println(o);
    }
}
