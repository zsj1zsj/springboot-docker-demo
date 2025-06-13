package org.example.controller;


import org.example.editor.CustomDeptEditor;
import org.example.model.Dept;
import org.example.model.Employee;
import org.example.model.TestModel;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test") // 为所有方法添加前缀路径
public class TestController {
    /**
     * 3.1 测试BeanWrapper
     * 演示BeanWrapper的类型转换能力，特别是String "1" 到 boolean true
     * 访问: http://localhost:8080/springmvcdemo/test/testWrapper
     */
    @RequestMapping(value = "/testWrapper", produces={"application/json; charset=UTF-8"})
    @ResponseBody
    public TestModel testWrapper() {
        TestModel tm = new TestModel();
        BeanWrapper bw = new BeanWrapperImpl(tm);
        // BeanWrapper 会利用内置的 PropertyEditor 或默认的类型转换规则进行转换
        // 对于 boolean 类型，"1", "true", "on", "yes" 都会被转换为 true
        bw.setPropertyValue("good", "1");
        System.out.println("testWrapper - TestModel good property: " + tm.isGood()); // 打印结果
        return tm;
    }
    /**
     * 3.2 测试不使用BeanWrapper的场景 (或者说，模拟BeanWrapperImpl内部没有注册相应Editor)
     * 实际上，BeanWrapperImpl 默认会注册很多内置的 PropertyEditor，
     * 这个例子可能想表达的是：如果没有默认的转换器，会失败。
     * 但在Spring实际运行中，对于基本类型它几乎总能处理。
     * 访问: http://localhost:8080/springmvcdemo/test/testNotUseWrapper
     *
     * 注意：这段代码在实际Spring环境中，可能不会像原文描述那样因为"没有对应的属性编辑器"而导致失败，
     * 因为 `BeanWrapperImpl` 默认已经集成了 `CustomBooleanEditor` 或者类似的类型转换逻辑。
     * 它的 `bw.setPropertyValue("good", "1")` 仍然会成功。
     * 原文可能想表达的是：**如果我们禁用了默认的PropertyEditor注册，或者自定义了一个不支持此转换的BeanWrapper**。
     * 但为了还原原文，我们保留这段代码。
     */
    @RequestMapping(value = "/testNotUseWrapper", produces={"application/json; charset=UTF-8"})
    @ResponseBody
    public TestModel testNotUseWrapper() {
        TestModel tm = new TestModel();
        // 这里的 new BeanWrapperImpl(false) 是指不使用自动注册 PropertyEditor，
        // 但通常会通过 setWrappedInstance(tm) 传递实例，并可能需要手动注册Editor
        // 注意：实际测试中，即使 new BeanWrapperImpl(false)，
        // Spring内部的PropertyEditorRegistrySupport仍会提供一些基本转换能力。
        BeanWrapperImpl bw = new BeanWrapperImpl(false); // 尝试不注册默认 PropertyEditors
        bw.setWrappedInstance(tm); // 绑定实例
        // 尝试设置属性
        // 理论上如果真的没有任何editor，这里会抛出异常，
        // 但在默认的Spring Boot/MVC环境中，通常会有一个能够将 "1" 转为 boolean 的策略。
        try {
            bw.setPropertyValue("good", "1");
            System.out.println("testNotUseWrapper - TestModel good property: " + tm.isGood()); // 打印结果
        } catch (Exception e) {
            System.err.println("Error in testNotUseWrapper: " + e.getMessage());
            // 如果真的转换失败，可以抛出，或者返回一个错误信息
        }
        return tm;
    }
    /**
     * 3.3 测试无注解对象参数绑定 (ServletModelAttributeMethodProcessor处理)
     * 演示 Spring MVC 如何将请求参数绑定到 POJO 对象，包括嵌套对象。
     * 访问: http://localhost:8080/springmvcdemo/test/testObj?id=1&name=s&age=12&dept.id=1&dept.name=20
     *
     * 注意：此方法在没有自定义 Dept 属性编辑器时，
     * 对于 `dept.id` 和 `dept.name` 这样的参数，Spring 会默认尝试将它们绑定到 `Employee` 对象中的 `dept` 属性。
     * 如果 `dept` 属性本身是 `Dept` 类型，且 `Dept` 类有对应的 `setId` 和 `setName` 方法，
     * Spring 会自动实例化 `Dept` 并绑定这些嵌套属性。
     */
    @RequestMapping(value = "/testObj", produces={"application/json; charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> testObj(Employee e) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("Employee", e);
        System.out.println("testObj - Employee: " + e); // 打印 Employee 对象
        return resultMap;
    }
    /**
     * 4. 编写自定义属性编辑器并注册到 WebDataBinder
     * `@InitBinder` 方法会在每次 Controller 方法被调用前初始化 WebDataBinder。
     * 在这个方法中，我们注册了 CustomDeptEditor，让 Spring 知道如何将 String 转换为 Dept 对象。
     *
     * 现在，再次访问 `testObj` 方法，但这次使用 `dept=1,research` 这样的格式：
     * http://localhost:8080/springmvcdemo/test/testObj?id=1&name=s&age=12&dept=1,research
     *
     * 此时，`dept` 参数的字符串 "1,research" 将被 CustomDeptEditor 处理，并转换为一个 Dept 对象。
     */
    @InitBinder
    public void initBinderDept(WebDataBinder binder) {
        // 注册 CustomDeptEditor，让它处理 Dept.class 类型的属性绑定
        binder.registerCustomEditor(Dept.class, new CustomDeptEditor());
        System.out.println("@InitBinder: CustomDeptEditor for Dept.class registered.");
    }
}