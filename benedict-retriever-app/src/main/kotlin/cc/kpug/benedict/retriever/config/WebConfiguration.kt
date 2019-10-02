package cc.kpug.benedict.retriever.config

import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.context.annotation.Configuration

@EnableWebMvc
@Configuration
class WebConfig: WebMvcConfigurer {
 
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }
}