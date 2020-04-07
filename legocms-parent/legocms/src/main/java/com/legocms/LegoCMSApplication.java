package com.legocms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import com.legocms.data.base.RepositoryFactoryBean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan("com.legocms")
@EntityScan("com.legocms.data.entities")
@EnableJpaRepositories(value = {"com.legocms.data.dao"}, repositoryFactoryBeanClass = RepositoryFactoryBean.class)
public class LegoCMSApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LegoCMSApplication.class);
    }

    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        return new OpenEntityManagerInViewFilter();
    }

	public static void main(String[] args) {
		SpringApplication.run(LegoCMSApplication.class, args);
	}

}
