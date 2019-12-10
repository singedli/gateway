package com.ocft.gateway.seataTest;

import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;

@Service
public class DemoService {

    public Map<String, Object> foo(Map<String, Object> input) {
        System.err.println("foo方法被执行！");
        if(input == null){
            return null;
        }
        if("true".equals(input.get("throwException"))){
            throw new DemoException("foo execute failed");
        }
        if("true".equals(input.get("throwExceptionRandomly"))){
            if(Math.random() > 0.5){
                throw new DemoException("foo execute failed");
            }
        }
        return input;
    }

    public Map<String, Object> compensateFoo(Map<String, Object> input) {
        System.err.println("compensateFoo方法被执行！");
        if(input == null){
            return null;
        }
        if("true".equals(input.get("throwException"))){
            throw new DemoException("compensateFoo execute failed");
        }
        if("true".equals(input.get("throwExceptionRandomly"))){
            if(Math.random() > 0.8){
                throw new DemoException("compensateFoo execute failed");
            }
        }
        return input;
    }

    public Map<String, Object> bar(Map<String, Object> input) {
        System.err.println("bar方法被执行！");
        if(input == null){
            return null;
        }
        if("true".equals(input.get("throwException"))){
            throw new DemoException("bar execute failed");
        }
        if("true".equals(input.get("throwExceptionRandomly"))){
            if(Math.random() > 0.5){
                throw new DemoException("bar execute failed");
            }
        }
        return input;
    }

    public Map<String, Object> compensateBar(Map<String, Object> input) {
        System.err.println("compensateBar方法被执行！");
        if(input == null){
            return null;
        }
        if("true".equals(input.get("throwException"))){
            throw new DemoException("compensateBar execute failed");
        }
        if("true".equals(input.get("throwExceptionRandomly"))){
            if(Math.random() > 0.8){
                throw new DemoException("compensateBar execute failed");
            }
        }
        return input;
    }

    public People complexParameterMethod(String name, int age, People people, People[] peopleArrya, List<People> peopleList, Map<String, People> peopleMap){
        return people;
    }

    public Career interfaceParameterMethod(Career career){
        return career;
    }

    public Map<String, Object> randomExceptionMethod(Map<String, Object> input) {

        double random = Math.random();
        if (random > 0.5) {
            throw new DemoException("randomExceptionMethod execute failed");
        }
        else {
            throw new RuntimeException(new ConnectException("Connect Exception"));
        }
    }

    public static class People {

        private String name;
        private int    age;

        private People[] childrenArray;
        private List<People> childrenList;
        private Map<String, People> childrenMap;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public People[] getChildrenArray() {
            return childrenArray;
        }

        public void setChildrenArray(People[] childrenArray) {
            this.childrenArray = childrenArray;
        }

        public List<People> getChildrenList() {
            return childrenList;
        }

        public void setChildrenList(List<People> childrenList) {
            this.childrenList = childrenList;
        }

        public Map<String, People> getChildrenMap() {
            return childrenMap;
        }

        public void setChildrenMap(Map<String, People> childrenMap) {
            this.childrenMap = childrenMap;
        }
    }

    public interface Career {

    }

    public static class Engineer implements Career {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}