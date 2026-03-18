package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountProperties {

    @Value("${account.default-amount}")
    private int initBalance;

    @Value("${account.transfer-commission}")
    private int transferCommission;

    public int getInitBalance() {
        return initBalance;
    }

    public int getTransferCommission() {
        return transferCommission;
    }
}
