package com.lhstack.tabbed.encoding.actions;

import com.lhstack.utils.I18nUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

public class Base64DecoderAction extends BaseEncodingAction {

    @Override
    public void doAction() {
        String text = sourceTextField.getText();
        try {
            if (StringUtils.isNotBlank(text)) {
                targetTextField.setText(new String(Base64.decodeBase64(text), StandardCharsets.UTF_8));
            } else {
                targetTextField.setText("请在左侧输入需要base64解码的内容");
            }
        } catch (Throwable err) {
            targetTextField.setText("base64解码失败,错误信息: " + err.getMessage());
        }
    }

    @Override
    public String encodingType() {
        return "Base64Decoding";
    }

    @Override
    public String encodingName() {
        return I18nUtils.getString("base64.decoding");
    }
}
