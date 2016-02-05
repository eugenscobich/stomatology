package md.stomatology.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages={"md.stomatology"}, excludeFilters = @ComponentScan.Filter(type=FilterType.REGEX, pattern={"md.stomatology.repository.*"}))
public class ApplicationConfig {

}