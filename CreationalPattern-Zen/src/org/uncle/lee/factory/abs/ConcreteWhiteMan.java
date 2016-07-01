package org.uncle.lee.factory.abs;

import org.uncle.lee.utils.LogUtils;

public class ConcreteWhiteMan extends AbstractWhiteHuman {
	@Override
	public void talk() {
		LogUtils.d(this.getClass().getSimpleName(), "I'm a white man.");
	}
}
