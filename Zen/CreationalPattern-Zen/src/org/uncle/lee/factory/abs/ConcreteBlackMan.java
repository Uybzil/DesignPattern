package org.uncle.lee.factory.abs;

import org.uncle.lee.utils.LogUtils;

public class ConcreteBlackMan extends AbstractBlackHuman {
	@Override
	public void talk() {
		LogUtils.d(this.getClass().getSimpleName(), "I'm a black man.");
	}
}
