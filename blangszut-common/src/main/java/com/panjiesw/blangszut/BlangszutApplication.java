package com.panjiesw.blangszut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;
import static com.panjiesw.blangszut.BlangszutProfiles.*;

public class BlangszutApplication extends SpringApplication {

	private static final Logger logger = LoggerFactory.getLogger(BlangszutApplication.class);

	public BlangszutApplication(Class<?> configClass) {
		super(configClass);
		logger.debug("New BlangszutanApplication instantiated");
	}

	/**
	 * Enforce mutual exclusivity and implicit activation of profiles as described in
	 * {@link com.panjiesw.blangszut.BlangszutProfiles}.
	 */
	@Override
	protected void configureProfiles(ConfigurableEnvironment environment, String[] args) {
		super.configureProfiles(environment, args);

		boolean standaloneActive = environment.acceptsProfiles(STANDALONE);
		boolean stagingActive = environment.acceptsProfiles(STAGING);
		boolean productionActive = environment.acceptsProfiles(PRODUCTION);

		if (stagingActive && productionActive) {
			throw new IllegalStateException(format("Only one of the following profiles may be specified: [%s]",
					arrayToCommaDelimitedString(new String[] { STAGING, PRODUCTION })));
		}

		if (stagingActive || productionActive) {
			logger.info(format("Activating '%s' profile because one of '%s' or '%s' profiles have been specified.",
					OPENSHIFT, STAGING, PRODUCTION));
			environment.addActiveProfile(OPENSHIFT);
		}
		else if (standaloneActive) {
			logger.info("The default 'standalone' profile is active because no other profiles have been specified.");
		}
		else {
			throw new IllegalStateException(format("Unknown profile(s) specified: [%s]. Valid profiles are: [%s]",
					arrayToCommaDelimitedString(environment.getActiveProfiles()),
					arrayToCommaDelimitedString(new String[] {
							arrayToCommaDelimitedString(environment.getDefaultProfiles()), STAGING, PRODUCTION })));
		}
	}
}
