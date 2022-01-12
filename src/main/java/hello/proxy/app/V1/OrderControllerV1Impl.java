package hello.proxy.app.V1;

public class OrderControllerV1Impl implements OrderControllerV1 {

    private OrderServiceV1 orderService;

    public OrderControllerV1Impl(OrderServiceV1 orderService) {
        this.orderService = orderService;
    }

    @Override
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "OK";
    }

    @Override
    public String noLog() {
        return "OK";
    }
}
