package com.labi.taskflowservice.client;

import com.labi.taskflowservice.config.InformaticaFeignConfig;
import com.labi.taskflowservice.entity.InformaticaRunResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "informatica-client",
        url = "${informatica.taskflow-url}",
        configuration = InformaticaFeignConfig.class
)
public interface InformaticaStartFeignClient {

    @PostMapping
    InformaticaRunResponse startTaskflow();


}
