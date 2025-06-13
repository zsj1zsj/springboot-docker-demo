package org.example.config;

import org.example.editor.CustomDeptEditor;
import org.example.model.Dept;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice // 标记这是一个控制器增强类
public class InitBinderControllerAdvice {
    // 这个 @InitBinder 方法会应用于所有Controller中的请求绑定
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 注册 CustomDeptEditor，以便将字符串转换为 Dept 对象
        binder.registerCustomEditor(Dept.class, new CustomDeptEditor());
        System.out.println("Global @InitBinder: CustomDeptEditor for Dept.class registered.");
    }
}
