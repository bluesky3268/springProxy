package hello.proxy.postProcessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 빈 후처리기 직접 만들어 사용하기
 */
public class BeanPostProcessorTest {

    @Test
    void basicConfig() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

        // 빈 이름은 beanA이지만 실제는 B가 등록되므로 조회할 때 찾을 수 없는 에러가 발생함
//        A beanA = (A) ac.getBean("beanA", A.class);
//        beanA.helloA();
        
        // 그래서 B로 바꿔준 후 다시 테스트
        B beanA = (B) ac.getBean("beanA", B.class);
        beanA.helloB();


        assertThat(beanA).isInstanceOf(B.class); // beanA라는 이름의 빈은 B
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(A.class)); // A는 빈으로 등록되지 않음
    }
    
    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        } // 빈 이름은 beanA이지만 실제는 B가 등록되므로 조회할 때 찾을 수 없는 에러가 발생함

        @Bean
        public AToBPostProcessor helloPostProcessor() {
            return new AToBPostProcessor();
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

    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor {

        /**
         * A클래스인 경우 B로 바꿔서 리턴
         * A가 아닌 경우 그냥 그대로 리턴
         * -> A를 B, 그러니까 완전히 다른 객체로 바꿔서 리턴할 수도 있는데..
         * 프록시 객체도 가능하겠지??
         */
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName : {}, bean : {}", beanName, bean);
            if (bean instanceof A) {
                return new B();
            }
            return bean;
        }

    }
}
