package spring.autoconfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@ComponentScan注解表示启用了组件扫描
@Configuration
@ComponentScan(basePackages ="autoconfig")
public class CDPlayerConfig { 
}
