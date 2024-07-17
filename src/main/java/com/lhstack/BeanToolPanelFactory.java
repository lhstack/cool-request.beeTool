package com.lhstack;

import com.lhstack.panel.BeanToolPanel;
import dev.coolrequest.tool.CoolToolPanel;
import dev.coolrequest.tool.ToolPanelFactory;

public class BeanToolPanelFactory implements ToolPanelFactory {
    @Override
    public CoolToolPanel createToolPanel() {
        return new BeanToolPanel();
    }
}
