package com.arnaugarcia.assessoriatorrelles.web.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Link {

	public String label();
	public String family();
	public String parent();

}
