package com.redhat.training;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

public interface ExpenseConfiguration {

	  boolean debugEnabled();

	  int rangeHigh();

	   int rangeLow();

}

