package com.labi.taskflowservice.client;

import com.labi.taskflowservice.config.InformaticaFeignConfig;
import com.labi.taskflowservice.entity.InformaticaTaskflowStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "informatica-status-client",
        url = "${informatica.status-url}",
        configuration = InformaticaFeignConfig.class
)
public interface InformaticaStatusFeignClient {
    @GetMapping("/active-bpel/services/tf/status/{runId}")
    InformaticaTaskflowStatus getTaskflowStatus(@PathVariable String runId);
}
