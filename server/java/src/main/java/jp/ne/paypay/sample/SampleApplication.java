package jp.ne.paypay.sample;

import jp.ne.paypay.ApiException;
import jp.ne.paypay.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SampleApplication {

  @Autowired
  private Environment env;

  public static void main(String[] args) throws JwtException, ApiException {
    SpringApplication.run(SampleApplication.class, args);
  }
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        String urls = env.getProperty("cors.urls");
        CorsRegistration reg = registry.addMapping("/**");
        if(urls !=null){
          for(String url: urls.split(",")) {
            reg.allowedOrigins(url);
          }
        }else{
          reg.allowedOrigins("http://localhost:8080");
        }

      }
    };
  }
}
