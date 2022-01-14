package hello.proxy.config.v1_proxy;

import hello.proxy.app.V2.OrderControllerV2;
import hello.proxy.app.V2.OrderRepositoryV2;
import hello.proxy.app.V2.OrderServiceV2;
import hello.proxy.config.v1_proxy.concreteProxy.OrderControllerConcreteProxy;
import hello.proxy.config.v1_proxy.concreteProxy.OrderRepositoryConcreteProxy;
import hello.proxy.config.v1_proxy.concreteProxy.OrderServiceConcreteProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 repositoryImpl = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(repositoryImpl, logTrace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 orderServiceImpl = new OrderServiceV2(orderRepositoryV2(logTrace));
        return new OrderServiceConcreteProxy(orderServiceImpl, logTrace);
    }

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        OrderControllerV2 orderControllerImpl = new OrderControllerV2(orderServiceV2(logTrace));
        return new OrderControllerConcreteProxy(orderControllerImpl, logTrace);
    }



}
