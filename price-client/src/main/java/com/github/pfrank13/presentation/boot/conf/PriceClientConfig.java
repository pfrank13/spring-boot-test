package com.github.pfrank13.presentation.boot.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author pfrank
 */
@Configuration
@ComponentScan(basePackages = {"com.github.pfrank13.presentation.boot.client.impl"})
public class PriceClientConfig {
}
