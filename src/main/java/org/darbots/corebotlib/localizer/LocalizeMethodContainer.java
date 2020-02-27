package org.darbots.corebotlib.localizer;

import org.darbots.corebotlib.hardware.DataReceiver;
import org.darbots.corebotlib.hardware.DataReceiverContainer;

public abstract class LocalizeMethodContainer implements DataReceiverContainer {
    @Override
    public DataReceiver getReceiver() {
        return null;
    }

    @Override
    public void setReceiver(DataReceiver receiver) {

    }
}
