package br.com.gustavobarbozamarques.springbootsecurityjwt.enums;

public enum TokenTypeEnum {
    ACCESS_TOKEN(1, "FlzRvmaWsCzUy5jESg2vIShRBsN0k9wIoskjNEeoIJFs1E1xSoblkszRLK8f"),
    REFRESH_TOKEN(1500, "3yYAMO04ZC76Hok3kiRE51aIg9XQg5PcEV3QlW477kiijShToEjkFxLypKe");

    private final Integer expirationMinutes;
    private final String secret;

    TokenTypeEnum(Integer expirationMinutes, String secret) {
        this.expirationMinutes = expirationMinutes;
        this.secret = secret;
    }

    public Integer getExpirationMinutes() {
        return expirationMinutes;
    }

    public String getSecret() {
        return secret;
    }
}
