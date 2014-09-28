package com.panjiesw.blangszut;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

class MvcConfig {
}

@Configuration
@Profile(BlangszutProfiles.STANDALONE)
class ClientResourcesConfig extends WebMvcConfigurerAdapter {

	@Value("${BLANGSZUT_HOME:}")
	private String blangszutPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!this.blangszutPath.isEmpty()) {
			registry.addResourceHandler("/js/**")
				.addResourceLocations("file://" + this.blangszutPath + "/blangszut-client/client/js/")
				.setCachePeriod(0);
			registry.addResourceHandler("/lib/**")
				.addResourceLocations("file://" + this.blangszutPath + "/blangszut-client/client/lib/")
				.setCachePeriod(0);
			registry.addResourceHandler("/css/**")
				.addResourceLocations("file://" + this.blangszutPath + "/blangszut-client/client/css/")
				.setCachePeriod(0);
			registry.addResourceHandler("/partials/**")
				.addResourceLocations("file://" + this.blangszutPath + "/blangszut-client/client/partials/")
				.setCachePeriod(0);
		}
	}
}
