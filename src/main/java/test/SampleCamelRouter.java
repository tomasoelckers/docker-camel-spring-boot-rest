package test;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SampleCamelRouter extends RouteBuilder {
	
	@Override
    public void configure() throws Exception {
			restConfiguration()
	        .component("servlet")
	        .bindingMode(RestBindingMode.json)
	        .dataFormatProperty("prettyPrint", "true")
	        .apiContextPath("/api-doc")
	            .apiProperty("api.title", "User API").apiProperty("api.version", "1.0.0")
	            .apiProperty("cors", "true");
			
			rest("/")
				.get().route().transform().constant("Hello Docker!!!");
			rest("/hello/{me}")
				.get().route().transform().simple("Bye ${header.me}");
			rest("/say/hello")
            	.get().route().transform().constant("Hello World");
			rest("/say/bye")
            	.get().consumes("application/json").route().transform().constant("Bye World").endRest()
            	.post().to("mock:update");
        }

}