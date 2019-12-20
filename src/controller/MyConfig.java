package controller;


import mvc.annotation.Configuration;
import mvc.config.ResourceHandlerRegistry;
import mvc.config.WebConfigurer;

@Configuration
public class MyConfig extends WebConfigurer {

	@Override
	public void addViewControllers(ResourceHandlerRegistry registry) {
		registry.addViewController("login", "/showLogin")
		.addViewController("regist", "/showRegist")
		.addViewController("list", "/showList")
		.addViewController("cart", "/showCart")
		.addViewController("order", "/showOrder")
		.addViewController("success", "/showSuccess")
		;
		
	}
		
}
