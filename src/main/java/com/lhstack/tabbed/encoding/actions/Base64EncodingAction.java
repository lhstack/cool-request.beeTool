package com.lhstack.tabbed.encoding.actions;

import com.lhstack.utils.I18nUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class Base64EncodingAction extends BaseEncodingAction {

    @Override
    public void doAction() {
        String text = sourceTextField.getText();
        try {
            if (StringUtils.isNotBlank(text)) {
                String base64Str = Base64.encodeBase64String(text.getBytes());
                targetTextField.setText(base64Str);
            } else {
                targetTextField.setText("请在左侧输入需要base64编码的内容");
            }
        } catch (Throwable err) {
            targetTextField.setText("base64编码失败,错误信息: " + err.getMessage());
        }
    }

    @Override
    public String encodingType() {
        return "Base64Encoding";
    }

    @Override
    public String encodingName() {
        return I18nUtils.getString("base64.encoding");
    }
}
