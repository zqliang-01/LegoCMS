package com.legocms.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.legocms.core.common.Constants;

@EnableWebMvc
@Configuration
public class LegoWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[] { "/resource/**" }).addResourceLocations(new String[] { "classpath:/static/" });
        registry.addResourceHandler(new String[] { "swagger-ui.html" }).addResourceLocations(new String[] { "classpath:/META-INF/resources/" });
        registry.addResourceHandler(new String[] { "/webjars/**" }).addResourceLocations(new String[] { "classpath:/META-INF/resources/webjars/" });
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration result = new CorsConfiguration();
        result.addAllowedOrigin(CorsConfiguration.ALL);
        result.addAllowedHeader(CorsConfiguration.ALL);
        result.addAllowedMethod(CorsConfiguration.ALL);
        result.setAllowCredentials(true);
        result.setMaxAge(3600L);
        return result;
    }

    @Bean
    CookieSerializer cookieSerializer() {
        DefaultCookieSerializer result = new DefaultCookieSerializer();
        result.setCookieName("HugeToken");
        return result;
    }

    @Bean
    HttpSessionIdResolver httpSessionIdResolver(CookieSerializer cookieSerializer) {
        CookieHttpSessionIdResolver result = new CookieHttpSessionIdResolver();
        result.setCookieSerializer(cookieSerializer);
        return result;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = converter.getFastJsonConfig();
        config.setCharset(Constants.DEFAULT_CHARSET);
        config.setSerializerFeatures(getSerializerFeatures());
        converter.setDefaultCharset(Constants.DEFAULT_CHARSET);
        converter.setSupportedMediaTypes(getSupportedMediaTypes());
        converters.add(0, converter);
    }

    public static SerializerFeature[] getSerializerFeatures() {
        return new SerializerFeature[] {
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.QuoteFieldNames,
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.PrettyFormat
        };
    }

    private static List<MediaType> getSupportedMediaTypes() {
        List<MediaType> results = new ArrayList<MediaType>();
        results.add(MediaType.TEXT_HTML);
        results.add(MediaType.APPLICATION_JSON);
        return results;
    }
}
