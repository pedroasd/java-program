import com.pedro.jmp.cloud.bank.impl.CentralBank;
import com.pedro.jmp.service.api.Service;
import com.pedro.jpm.bank.api.Bank;

module application {
    requires jmp.service.api;
    requires jpm.bank.api;
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;
    requires jpm.dto;

    uses Service;
    uses Bank;
}