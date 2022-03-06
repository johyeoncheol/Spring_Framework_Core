package hello.advanced.app.v5;

import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final LogTrace trace;

    //RequiredArgsConstructor을 대신함
//    public OrderService(OrderRepositoryV0 orderRepositoryV0) {
//        this.orderRepositoryV0 = orderRepositoryV0;
//    }

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
       template.execute("OrderServiceV4.orderItem()");
    }
}
