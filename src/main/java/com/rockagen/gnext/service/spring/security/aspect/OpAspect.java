/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rockagen.gnext.service.spring.security.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.rockagen.commons.annotation.OPLog;
import com.rockagen.commons.util.CommUtil;

/**
 * Operate log aspect weaver,using <code>@Around</code> Mode
 * 
 * @author RA
 */
@Component("operateLog")
@Aspect
public class OpAspect {

	// ~ Instance fields ==================================================

	private static Log log = LogFactory.getLog("OperateLog");

	// ~ Methods ==================================================

	/**
	 * Define need cut point
	 */
	@Pointcut("execution(* com.rockagen.gnext.controller.*.*(..))")
	private void pointcutJoin() {
	}

	/**
	 * Define need cut point2
	 */
	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Pointcut("within(com.rockagen.gnext.service.spring.XXXServ)")
	 * private void pointcutJoin2() { }
	 */

	/**
	 * Define not need cut point
	 */
	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Pointcut("!within(com.rockagen.gnext.controller.RandomPictureAction)"
	 * ) private void pointcutNoJoin() { }
	 */

	/**
	 * Define not need cut point 2
	 */
	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Pointcut("!within(com.rockagen.gnext.controller.AbstractBaseAction)")
	 * private void pointcutNoJoin2() { }
	 */

	/**
	 * Define not need cut point 3
	 */
	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Pointcut("!execution(* com.rockagen.gnext.controller.json.*.*(..))")
	 * private void pointcutNoJoin3() { }
	 */

	/**
	 * Define point cut group
	 */
	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Pointcut(
	 * "pointcutJoin() && pointcutNoJoin() && pointcutNoJoin2() && pointcutNoJoin3()"
	 * ) private void pointcutServiceGroup1() { }
	 */

	/**
	 * Define all point cut
	 */
	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Pointcut("pointcutJoin2() || pointcutServiceGroup1()") private void
	 * pointcutService() { }
	 */

	@Pointcut("pointcutJoin()")
	private void pointcutService() {
	}

	/**
	 * Before
	 * 
	 * @param methodName
	 */
	private void beforeJoinPoint(String methodName) {

		String detail = "{IP: " + SS3Tools.getRemoteAddress() + " User [  "
				+ SS3Tools.getUserInfo().getNickname() + "  ] do --> \""
				+ methodName + "\" }";
		log.info(detail);
	}

	/**
	 * After
	 * 
	 * @param methodName
	 */
	private void afterJoinPoint(String methodName) {
		String detail = "{IP: " + SS3Tools.getRemoteAddress() + " User [  "
				+ SS3Tools.getUserInfo().getNickname() + "  ]  success --> \""
				+ methodName + "\" }";
		log.info(detail);
	}

	/**
	 * [Main]
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("pointcutService()")
	public Object loggerOpt(ProceedingJoinPoint pjp) throws Throwable {

		Object obj = null;
		// Obtain the will executing method and target object type
		String methodName = pjp.getSignature().getName();
		if (isPoint(methodName)) {
			// Get class info
			Class<? extends Object> targetClass = pjp.getTarget().getClass();
			Method method = targetClass.getMethod(methodName);
			beforeJoinPoint(getMethodDesc(method));
			// call target object method
			obj = pjp.proceed();
			afterJoinPoint(getMethodDesc(method));
		} else {
			obj = pjp.proceed();
		}

		return obj;
	}

	/**
	 * Exception handle
	 * 
	 * @param ex
	 */
	@AfterThrowing(pointcut = "pointcutService()", throwing = "ex")
	public void loggerException(Exception ex) {

		String detail = "{IP: " + SS3Tools.getRemoteAddress() + " User [  "
				+ SS3Tools.getUserInfo().getNickname()
				+ "  ] failure!  \n Reason :" + ex.toString() + "}";
		log.error(detail);
	}

	/**
	 * Get method description
	 * 
	 * @param method
	 * @return method description
	 */
	private String getMethodDesc(Method method) {

		boolean hasAnnotation = method.isAnnotationPresent(OPLog.class);

		if (hasAnnotation) {
			OPLog annotation = method.getAnnotation(OPLog.class);

			String methodDescp = annotation.description();
			if (log.isDebugEnabled()) {
				log.debug("Target method: " + method.getName()
						+ " Description: " + methodDescp);
			}
			return methodDescp;
		} else {
			return method.getName();
		}
	}

	/**
	 * Method filter
	 * 
	 * @param methodName
	 * @return
	 */
	private boolean isPoint(String methodName) {
		// Target method must not null
		if (!StringUtils.isBlank(methodName)) {
			// Exclude setter and getter
			if (!CommUtil.startsWithAny(methodName,
					new String[] { "set", "get" })) {
				return true;
			}
		}
		return false;
	}
}
