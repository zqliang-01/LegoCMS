package com.legocms.core.web;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Conventions;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
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
import com.legocms.core.exception.CoreException;
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

    @Bean
    public ServletContextInitializer resourceInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                new WebApplicationInitializer() {

                    @Override
                    public void onStartup(ServletContext servletContext) throws ServletException {
                        Dynamic webfileRegistration = servletContext.addServlet("webfileServlet", new HttpRequestHandlerServlet());
                        webfileRegistration.setLoadOnStartup(0);
                        webfileRegistration.addMapping(new String[] { "/static/*", "/favicon.ico" });
                        Filter[] filters = getServletFilters();
                        if (filters != null) {
                            for (Filter filter : filters) {
                                registerServletFilter(servletContext, filter, new String[] { "webfileServlet" });
                            }
                        }
                    }

                }.onStartup(servletContext);
            }
        };
    }

    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter, String[] servletNames) {
        String filterName = Conventions.getVariableName(filter);
        FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, filter);
        if (null == registration) {
            int counter = -1;
            while (-1 == counter || null == registration) {
                counter++;
                registration = servletContext.addFilter(filterName + "#" + counter, filter);
                CoreException.check(counter < 100, "Failed to register filter '" + filter + "'." + "Could the same Filter instance have been registered already?");
            }
        }
        registration.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, servletNames);
        return registration;
    }

    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(Constants.DEFAULT_CHARSET_NAME);
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] { characterEncodingFilter };
    }
}
