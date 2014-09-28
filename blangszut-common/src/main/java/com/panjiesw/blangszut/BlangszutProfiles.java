package com.panjiesw.blangszut;

public final class BlangszutProfiles {

	/**
	 * When active, indicates that the application is being deployed to the "staging"
	 * space of a Cloud Foundry instance. Implicitly activates {@link #OPENSHIFT}
	 * profile, and is mutually exclusive with {@link #PRODUCTION} and {@link #STANDALONE}
	 * profiles.
	 */
	public static final String STAGING = "staging";

	/**
	 * When active, indicates that the application is being deployed to the "production"
	 * space of a Cloud Foundry instance. Implicitly activates {@link #OPENSHIFT}
	 * profile, and is mutually exclusive with {@link #STAGING} and {@link #STANDALONE}
	 * profiles.
	 */
	public static final String PRODUCTION = "production";

	/**
	 * Implicitly activated when either {@link #PRODUCTION} or {@link #STAGING} profiles
	 * are active, this profile indicates that the application is running on Cloud Foundry
	 * as opposed to running {@link #STANDALONE}, and should expect to find data sources,
	 * etc as Cloud Foundry services, as opposed to finding them in-memory.
	 *
	 * @see sagan.util.service.db.CloudFoundryDatabaseConfig
	 */
	public static final String OPENSHIFT = "cloudfoundry";

	/**
	 * The default profile for any {@link BlangszutApplication}. Indicates that the
	 * application is running locally, i.e. on a developer machine as opposed to running
	 * on {@link #OPENSHIFT} and should expect to find data sources, etc in-memory as
	 * opposed to finding them as Cloud Foundry services. This profile constant is named
	 * "STANDALONE" to clearly communicate its role, but its value is actually "default",
	 * as this is the "reserved default profile name" in Spring. This means that
	 * "STANDALONE" will always be treated as the default profile without requiring any
	 * code to programmatically activate it. This makes running integration tests that
	 * expect in-memory resources simple to set up.
	 *
	 * @see com.panjiesw.blangszut.PersistenceConfig
	 * @see org.springframework.core.env.AbstractEnvironment#RESERVED_DEFAULT_PROFILE_NAME
	 */
	public static final String STANDALONE = "default";
}
