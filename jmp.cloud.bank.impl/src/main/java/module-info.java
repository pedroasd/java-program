import com.pedro.jmp.cloud.bank.impl.InvestmentBank;
import com.pedro.jpm.bank.api.Bank;

module jmp.cloud.bank.impl {
    requires transitive jpm.bank.api;
    requires jpm.dto;
    exports com.pedro.jmp.cloud.bank.impl;
    provides Bank with InvestmentBank;
}