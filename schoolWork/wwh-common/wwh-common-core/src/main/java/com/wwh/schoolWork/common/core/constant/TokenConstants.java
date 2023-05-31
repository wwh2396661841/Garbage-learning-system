package com.wwh.schoolWork.common.core.constant;

/**
 * Token的Key常量
 *
 * @author tyky zhi
 */
public class TokenConstants
{
    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Auth";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "wwh ";

    /**
     * 令牌秘钥
     */
    public final static String SECRET = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 密钥认证模式令牌前缀
     */
    public static final String SECRET_PREFIX = "Secret";

    /**
     * 密钥认证模式错误令牌前缀
     */
    public static final String ERROR_SECRET_PREFIX = "SecretError ";

    /**
     * App 应用
     */
    public static final String APP = "wwh-App";

    /**
     * nonce 随机数
     */
    public static final String NONCE = "wwh-Nonce";

    /**
     * signature 签名
     */
    public static final String SIGNATURE = "wwh-Signature";

    /**
     * nonce 时间戳
     */
    public static final String TIMESTAMP = "wwh-Timestamp";

}
