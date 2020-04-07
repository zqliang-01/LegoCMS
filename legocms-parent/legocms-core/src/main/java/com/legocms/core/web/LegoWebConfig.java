package com.legocms.core.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.legocms.core.common.Constants;
import com.legocms.core.web.session.ISessionInterceptor;

@EnableWebMvc
@Configuration
public class LegoWebConfig implements WebMvcConfigurer {

    @Autowired
    private List<ISessionInterceptor> sessionInterceptes;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[] { "/resource/**" }).addResourceLocations(new String[] { "classpath:/static/" });
        registry.addResourceHandler(new String[] { "swagger-ui.html" }).addResourceLocations(new String[] { "classpath:/META-INF/resources/" });
        registry.addResourceHandler(new String[] { "/webjars/**" }).addResourceLocations(new String[] { "classpath:/META-INF/resources/webjars/" });
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (ISessionInterceptor sessionInterceptor : sessionInterceptes) {
            registry.addInterceptor(sessionInterceptor)
            .addPathPatterns(sessionInterceptor.getPathPatterns())
            .excludePathPatterns(sessionInterceptor.getExcludePathPatterns());
        }
    }

    @Bean
    public CorsFilter corsFilter() {
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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, getJsonMessageConverter());
        converters.add(1, new StringHttpMessageConverter(Constants.DEFAULT_CHARSET));
    }

    @Bean
    public LocaleResolver localeResolver(Environment env) {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("legocms.locale");
        localeResolver.setCookieMaxAge(30 * 24 * 3600);
        localeResolver.setDefaultLocale(Locale.forLanguageTag("zh"));
        return localeResolver;
    }

    @Bean
    public FastJsonHttpMessageConverter getJsonMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = converter.getFastJsonConfig();
        config.setCharset(Constants.DEFAULT_CHARSET);
        config.setSerializerFeatures(getSerializerFeatures());
        converter.setDefaultCharset(Constants.DEFAULT_CHARSET);
        converter.setSupportedMediaTypes(getSupportedMediaTypes());
        return converter;
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
        results.add(MediaType.APPLICATION_JSON);
        return results;
    }
}
