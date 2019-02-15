/*
 * 文件名：AcessTokenEntity.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;

/**
 * accessToken凭据
 * @author zhangyunzhen
 * @version 2017年7月12日
 * @see AccessToken
 * @since
 */
public class AccessToken {

    /**
     * 调用微信接口的唯一凭据
     */
    public static String ACCESS_TOKEN = "Q0NmlZuRZ0ejVeVSFqW5t21vc4JMtXmWXjI4EIubzwu4crJDyBVVmdtMnODxkxgTCWaUnghWPbbBlKvRovoeqk6Lj9Xe0DlxPlkjUrL6ikHtxnvn23ST2lChm-io8qRJOe1yEKNB49NXQW5Qzcu_Hrd5tUW-XIDXbK2tIIiATxGSqAnIKVDMlRTnps-MQhhaCYwkC_wGRyoqYVY8nFqvYeUe57Fj123fhtIXbW3-4gSdW9_KZ2DKq5oubeIZokXdTVc0rVUEX51gbTTmN3rs-Z_1egkcGL8Ru-eMbb4dtnc";

    /**
     * 企业微信能力开发平台access_token 
     */
    public static String ENTERPRISE_WEIXIN_ACCESS_TOKEN_COPC = "N_KG_EVnOVM8tXGk1-8_mwg1DAY3QaTeUaoo8Oj5JZEfw4Z7IIJtmSkbXjzjvum2EsNBr-jZD-ucpJqLdBJ8PJ1uqTsgI854u9xBAZ7iCpGUwGzKVsIexfxOChD2O6KN3vVmXPxN47fgjOch9-oCvnV1MX0OdFX6JkjS635BckBBgHk7aVWSZsFxXHqhb127GE4n0Sfs6Wenk-B2AE3lGOx6Wpeevmb6zVdadKPRqe68_pnIrH-b3aL7RPLzdkyfg3Oz0nHZJswtoEkPoFCvVLZxkN4ykjkKINqnRhiSvv8";

    /**
     * 企业微信通讯录access_token
     */
    public static String ENTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST = "w8hTf3t7wLwrjGjVkScqaUsN_DyR_pNkZOWtaCiUYevr9ZTfolMPfYiAs7YJmWnnnHXAGTJK6a17_DI8ZH7Z8HCh393AbrdFFJK6recrAgWLCFSX6JNSY_UIOgbjF5snd2A2trDsUBa4HLrlOjohtniSSZ2ApSHpT_vyw4Vu_AVwod7Vslhm_qNLYMmKlJ_onbBBqrdjV1NvxlhhjYb29j7PYCbEnA_jfy4PVMe7Uf8vHIQeoVsVtyNKhQ4IDpeT5kqrpGjg_IU6ZM0NiVAWYu1Dug9-Y_FW3kIIfeIo2ps";

    public static void setACCESS_TOKEN(String aCCESS_TOKEN) {
        ACCESS_TOKEN = aCCESS_TOKEN;
    }

    public static void setENTERPRISE_WEIXIN_ACCESS_TOKEN_COPC(String eNTERPRISE_WEIXIN_ACCESS_TOKEN_COPC) {
        ENTERPRISE_WEIXIN_ACCESS_TOKEN_COPC = eNTERPRISE_WEIXIN_ACCESS_TOKEN_COPC;
    }

    public static void setENTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST(String eNTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST) {
        ENTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST = eNTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST;
    }

}
