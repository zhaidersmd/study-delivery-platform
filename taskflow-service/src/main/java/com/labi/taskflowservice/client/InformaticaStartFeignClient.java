package com.labi.taskflowservice.client;

import com.labi.taskflowservice.config.InformaticaFeignConfig;
import com.labi.taskflowservice.entity.InformaticaRunResponse;
import com.labi.taskflowservice.entity.InformaticaTaskflowStatus;
import com.labi.taskflowservice.entity.StartTaskflowRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "informatica-client",
        url = "${informatica.taskflow-url}",
        configuration = InformaticaFeignConfig.class
)
public interface InformaticaStartFeignClient {

    @PostMapping
    InformaticaRunResponse startTaskflow();


}
