package org.darbots.corebotlib.localizer;

import org.darbots.corebotlib.hardware.DataReceiver;

public abstract class LocalizeMethod implements DataReceiver<LocalizeMethodContainer> {
    @Override
    public LocalizeMethodContainer getContainer() {
        return null;
    }

    @Override
    public void setContainer(LocalizeMethodContainer container) {

    }
}
