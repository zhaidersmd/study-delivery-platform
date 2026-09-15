package com.labi.taskflowservice.client;

import com.labi.taskflowservice.entity.InformaticaRunResponse;
import com.labi.taskflowservice.entity.StartTaskflowRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "informatica-client",
        url = "${informatica.baseurl}"
)
public interface InformaticaFeignClient {

    @PostMapping("/mock/informatica/taskflows/run")
    InformaticaRunResponse startTaskflow(@RequestBody StartTaskflowRequest request);
}
