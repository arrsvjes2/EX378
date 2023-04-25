package com.redhat.training;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import java.util.Optional;

public interface ExpenseConfiguration {

	  boolean debugEnabled();

	  int rangeHigh();

	   int rangeLow();
	
	  //Optional<String> debugMessage();

}

