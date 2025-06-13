package org.example.editor;

import org.example.model.Dept;

import java.beans.PropertyEditorSupport;

public class CustomDeptEditor extends PropertyEditorSupport {
    /**
     * 将字符串文本转换为 Dept 对象并设置值。
     * 这是自定义属性编辑器的核心方法。
     * @param text 要转换的字符串值
     * @throws IllegalArgumentException 如果字符串格式不符合预期
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        System.out.println("CustomDeptEditor: setAsText called with text: " + text);
        if (text != null && text.contains(",")) {
            Dept dept = new Dept();
            String[] arr = text.split(",");
            try {
                dept.setId(Integer.parseInt(arr[0].trim()));
                dept.setName(arr[1].trim());
                setValue(dept); // 将转换后的 Dept 对象设置给属性
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid Dept ID format: " + arr[0], e);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid Dept format, missing name part: " + text, e);
            }
        } else {
            throw new IllegalArgumentException("Dept parameter format is incorrect, expected 'id,name', but got: " + text);
        }
    }
    /**
     * (可选) 将 Dept 对象转换为字符串文本。
     * 如果需要在界面上显示 Dept 对象（例如在表单中回显），这个方法会用到。
     */
    @Override
    public String getAsText() {
        Object value = getValue();
        if (value instanceof Dept) {
            Dept dept = (Dept) value;
            return dept.getId() + "," + dept.getName();
        }
        return super.getAsText();
    }
}
