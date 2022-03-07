package hello.proxy.pureproxy.concreteProxy.code;

public class ConcreteClient {

    private ConcreteLogic concreteLogic; // ConcreteLogic, TimeProxy 모두 적용 가능

    public ConcreteClient(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    public void execute(){
        concreteLogic.operation();
    }

}
