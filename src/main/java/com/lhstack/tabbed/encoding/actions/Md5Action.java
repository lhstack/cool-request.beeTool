package com.lhstack.tabbed.encoding.actions;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class Md5Action extends BaseEncodingAction{
    @Override
    public String encodingType() {
        return "Md5";
    }

    @Override
    public String encodingName() {
        return "Md5";
    }

    @Override
    public void doAction() {
        String text = sourceTextField.getText();
        try {
            if (StringUtils.isNotBlank(text)) {
                targetTextField.setText(DigestUtils.md5Hex(text));
            } else {
                targetTextField.setText("请在左侧输入需要Md5编码的内容");
            }
        } catch (Throwable err) {
            targetTextField.setText("Md5编码失败,错误信息: " + err.getMessage());
        }
    }
}
