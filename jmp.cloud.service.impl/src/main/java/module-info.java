import com.pedro.jmp.cloud.service.impl.ServiceImpl;
import com.pedro.jmp.service.api.Service;

module jmp.cloud.service.impl {
    requires jpm.dto;
    requires transitive jmp.service.api;
    exports com.pedro.jmp.cloud.service.impl;
    provides Service with ServiceImpl;
}