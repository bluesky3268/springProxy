package hello.proxy.config.v4_postProcessor.postProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {
    /**
     * 특정 패키지 하위에 있는 클래스에 대해서만 프록시를 적용
     */
    private final String basePackage;
    private final Advisor advisor;

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("beanName : {}, bean : {}", beanName, bean.getClass());

        // 프록시 적용 대상 여부 체크 후
        // 프록시 적용 대상이 아니면 원본 그대로 진행
        String beanPackageName = bean.getClass().getPackageName();
        if (!beanPackageName.startsWith(basePackage)) {
            return bean;
        }

        // 프록시 적용 대상
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);
        Object proxy = proxyFactory.getProxy();
        log.info("create proxy : target={}, proxy={}", bean.getClass(), proxy.getClass());

        return proxy;
    }
}
