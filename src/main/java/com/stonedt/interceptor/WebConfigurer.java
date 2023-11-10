package com.stonedt.interceptor;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
  @Autowired
  private LoginHandlerInterceptor loginInterceptor;
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(this.loginInterceptor)
      .addPathPatterns(new String[] { "/**" }).excludePathPatterns("/login",
                    "/code",
                    "/logout",
                    "/verifyacount",
                    "/css/**",
                    "/font-awesome/**",
                    "/img/**",
                    "/js/**",
                    "/notice/getNewNotice",
                    "/synthesize/getNewSynthesize");
  }
  
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.BrowserCompatible });
    SerializeConfig serializeConfig = SerializeConfig.globalInstance;
    serializeConfig.put(BigInteger.class, (ObjectSerializer)ToStringSerializer.instance);
    serializeConfig.put(Long.class, (ObjectSerializer)ToStringSerializer.instance);
    serializeConfig.put(long.class, (ObjectSerializer)ToStringSerializer.instance);
    fastJsonConfig.setSerializeConfig(serializeConfig);
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    fastConverter.setSupportedMediaTypes(fastMediaTypes);
    fastConverter.setFastJsonConfig(fastJsonConfig);
    converters.add(fastConverter);
  }
}
