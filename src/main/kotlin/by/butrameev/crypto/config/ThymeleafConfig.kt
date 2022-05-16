package by.butrameev.crypto.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveView
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver
import org.thymeleaf.templatemode.TemplateMode

@Configuration
class ThymeleafConfig: WebFluxConfigurer{

  @Bean
  fun templateResolver(): SpringResourceTemplateResolver? {
    val resolver = SpringResourceTemplateResolver()
    resolver.name = "index"
    resolver.characterEncoding = "UTF-8"
    resolver.prefix = "src/main/resources/templates/"
    resolver.suffix = ".html"
    resolver.isCacheable = false
    resolver.templateMode = TemplateMode.HTML
    return resolver
  }

  @Bean
  fun templateEngine(): ISpringWebFluxTemplateEngine? {
    val engine = SpringWebFluxTemplateEngine()
    engine.enableSpringELCompiler = true
    engine.setTemplateResolver(templateResolver())
    return engine
  }

  @Bean
  fun viewResolver(): ThymeleafReactiveViewResolver {
    val viewResolver = ThymeleafReactiveViewResolver()
    viewResolver.templateEngine = templateEngine()
    viewResolver.viewNames = arrayOf("index", "index1", "index2")
    viewResolver.order = 1
    return viewResolver
  }

  @Bean
  fun indexView(): ThymeleafReactiveView {
    val indexView = ThymeleafReactiveView()
    indexView.isRedirectView
    indexView.templateName = "index"
    indexView.requestContextAttribute = "userDto"
    return indexView
  }
}
