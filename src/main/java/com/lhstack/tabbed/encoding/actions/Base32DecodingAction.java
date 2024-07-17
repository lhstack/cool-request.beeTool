package com.lhstack.tabbed.encoding.actions;

import com.lhstack.utils.I18nUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base32;

import java.nio.charset.StandardCharsets;

public class Base32DecodingAction extends BaseEncodingAction {
    @Override
    public String encodingType() {
        return "Base32Decoding";
    }

    @Override
    public String encodingName() {
        return I18nUtils.getString("base32.decoding");
    }

    @Override
    public void doAction() {
        String text = sourceTextField.getText();
        try {
            if (StringUtils.isNotBlank(text)) {
                targetTextField.setText(new String(Base32.decode(text), StandardCharsets.UTF_8));
            } else {
                targetTextField.setText("请在左侧输入需要base32解码的内容");
            }
        } catch (Throwable err) {
            targetTextField.setText("base32解码失败,错误信息: " + err.getMessage());
        }
    }
}
