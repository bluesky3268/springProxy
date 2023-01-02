package hello.proxy.postProcessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 빈 후처리기 예제
 */
public class BasicTest {

    @Test
    void basicConfig() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(BasicConfig.class);

        A beanA = (A) ac.getBean("beanA", A.class);
        beanA.helloA();

        // B는 스프링 빈으로 등록하지 않았음
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(B.class));
    }

    /**
     * 빈 후처리기
     * 빈 후처리기를 사용하려면 BeanPostProcessor 인터페이스를 구현해서 빈으로 등록하면 됨
     *  - postProcessBeforeInitialization() : 객체 생성 이후 @PostConstruct와 같이 초기화가 되기 '전' 호출되는 메서드
     *  - postProcessAfterInitialization() : 객체 생성 이후 @PostConstruct와 같이 초기화가 되고 난 '후' 호출되는 메서드
     */

    @Slf4j
    @Configuration
    static class BasicConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }
    }


    @Slf4j
    static class A {
        public void helloA() {
            log.info("helloA");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("helloB");
        }
    }
}
