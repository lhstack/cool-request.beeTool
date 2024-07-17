package com.lhstack.tabbed.encoding.actions;

import com.lhstack.utils.I18nUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

public class HexEncodingAction extends BaseEncodingAction {

    @Override
    public void doAction() {
        String text = sourceTextField.getText();
        try {
            if (StringUtils.isNotBlank(text)) {
                targetTextField.setText(Hex.encodeHexString(text.getBytes(StandardCharsets.UTF_8)));
            } else {
                targetTextField.setText("请在左侧输入需要进行Hex编码的内容");
            }
        } catch (Throwable err) {
            targetTextField.setText("Hex编码失败,错误信息: " + err.getMessage());
        }
    }

    @Override
    public String encodingType() {
        return "HexEncoding";
    }

    @Override
    public String encodingName() {
        return I18nUtils.getString("hex.encoding");
    }
}
