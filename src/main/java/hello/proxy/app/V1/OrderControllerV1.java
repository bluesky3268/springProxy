package hello.proxy.app.V1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 스프링은 @Controller나 @RequestMapping이 있어야 컨트롤러로 인식
// -> 컨트롤러로 인식해야 HTTP URL이 매핑이 되서 동작함
@RequestMapping
@ResponseBody
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("v1/no-log")
    String noLog();
}
