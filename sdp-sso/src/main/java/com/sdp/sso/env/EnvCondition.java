package com.sdp.sso.env;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class EnvCondition implements Condition {
	

	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return "true".equals(context.getEnvironment().getProperty("base.platform.enable"));
	}
}
