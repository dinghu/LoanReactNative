package com.xinyonghua.xyh.udcredit;

import android.content.Context;

import com.authreal.api.AuthBuilder;
import com.authreal.api.OnResultListener;

import java.util.Date;

public class UdCreditUtils {
    private static String authKey = "pubKey";

    public static void auth(Context context, final UdCreditAuthListener udCreditAuthListener) {

        String id = "orderid_" + new Date().getTime();
        AuthBuilder mAuthBuilder = new AuthBuilder(id, authKey, null, new OnResultListener() {
            @Override
            public void onResult(String s) {
                udCreditAuthListener.onResult(s);
            }
        });

        //下文调用方法做为范例，请以对接文档中的调用方法为准
        mAuthBuilder.faceAuth(context);
    }

//    public static void auth(Context context, String partnerOrderNumber, UdCreditAuthListener udCreditAuthListener) {
//        // 订单号商户自己生成：不超过36位，非空，不能重复
//        String partner_order_id = partnerOrderNumber;
//        //商户pub_key ： 开户时通过邮件发送给商户
//        String pubKey = UdCreditConstant.pubKey;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        //签名时间：有效期5分钟，请每次重新生成 :签名时间格式：yyyyMMddHHmmss
//        String sign_time = simpleDateFormat.format(new Date());
//        // 商户 security_key  ：  开户时通过邮件发送给商户
//        String security_key = UdCreditConstant.security_key;
//        // 签名规则
//        String singStr = "pub_key=" + pubKey + "|partner_order_id=" + partner_order_id + "|sign_time=" + sign_time + "|security_key=" + security_key;
//        //生成 签名
//        String sign = Md5.encrypt(singStr);
//        /** 以上签名 请在服务端生成，防止key泄露 */
//        auth(context, partnerOrderNumber, pubKey, sign_time, sign, udCreditAuthListener);
//    }
//
//    public static void auth(Context context, String partnerOrderNumber, String pubKey, String sign_time, String sign, final UdCreditAuthListener udCreditAuthListener) {
//        AuthBuilder authBuilder = new AuthBuilder(partnerOrderNumber, pubKey, sign_time, sign, new OnResultListener() {
//            @Override
//            public void onResult(int op_type, String result) {
//                Log.e("MainActivity:result", op_type + "//" + result);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    /***
//                     * 业务处理成功（不是认证成功）
//                     */
//                    if (jsonObject.has("success") && jsonObject.getString("success").equals("true")) {
//                        /** 业务处理成功 ，可以根据不同的模块 处理数据 */
//                        switch (op_type) {
//                            case AuthBuilder.OPTION_ERROR:
//                                //// TODO:  error
//                                break;
//                            case AuthBuilder.OPTION_OCR:
//                                //// TODO:  OCR扫描 回调
//                                udCreditAuthListener.onOcr();
//                                break;
//                            case AuthBuilder.OPTION_VERIFY:
//                                //// TODO:  实名验证简项 回调
//                                break;
//                            case AuthBuilder.OPTION_LIVENESS:
//                                //// TODO:  活体 回调
//                                udCreditAuthListener.onLiveNess();
//                                break;
//                            case AuthBuilder.OPTION_VIDEO:
//                                //// TODO:  视频存证 回调
//                                break;
//                            case AuthBuilder.OPTION_COMPARE_FACE:
//                                //// TODO:  人脸比对 回调
//                                udCreditAuthListener.onFace();
//                                break;
//                            case AuthBuilder.OPTION_VERIFY_COMPARE:
//                                //// TODO:  人像比对 回调
//                                break;
//                        }
//                    } else {
//                        /***
//                         * 业务处理失败
//                         */
//                        String message = jsonObject.getString("message");
//                        String errorcode = jsonObject.getString("errorcode");
//                        /** 打印错误日志，可根据文档定位问题 */
//                        Log.d("MainActivity", errorcode + ":" + message);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        /** 添加 身份证ocr识别 模块
//         *
//         *  非必须添加
//         *  是否需要添加该模块，根据自己业务需要来判断
//         *  执行顺序和添加顺序一致。如有疑问，请联系有盾对接人员
//         *
//         * */
//        authBuilder.addFollow(AuthComponentFactory.getOcrComponent()
//                /**设置展示确认页面 ： 非必需 */
//                .showConfirm(true)
//                /**设置展示确认页面 ： 非必需 */
//                .mosaicIdName(false)
//                /**设置展示确认页面 ： 非必需 */
//                .mosaicIdNumber(false)
//                /**设置异步通知地址 ： 非必需 */
//                .setNotifyUrl("http:......"))//更多设置项目参见文档：http://static.udcredit.com/doc/idsafe/android/V43/index.html
//
//                /** 添加 活体检测 模块
//                 *
//                 *  非必须添加
//                 *  是否需要添加该模块，根据自己业务需要来判断
//                 *  执行顺序和添加顺序一致。如有疑问，请联系有盾对接人员
//                 *
//                 * */
//                .addFollow(AuthComponentFactory.getLivingComponent()
//                        /** 声音开关 该方法已废弃*/
//                        //                                .setVoiceEnable(false)
//                        /**设置异步通知地址 ： 非必需 */
//                        .setNotifyUrl("http:......"))//更多设置项目参见文档：http://static.udcredit.com/doc/idsafe/android/V43/index.html
//                /**
//                 * 人脸比对模块
//                 *
//                 */
//                .addFollow(AuthComponentFactory.getCompareFaceComponent()
//                        // 此示例对比项A为OCR人像图片
//                        .setCompareItemA(CompareItemFactory.getCompareItemBySessionId(CompareItemSession.SessionType.PHOTO_IDENTIFICATION))
//                        //此示例对比项B为活体过程中截图
//                        .setCompareItemB(CompareItemFactory.getCompareItemBySessionId(CompareItemSession.SessionType.PHOTO_LIVING))
//                        //设置异步通知地址 ： 非必需
//                        .setNotifyUrl("http:......")).start(context);
//    }

}
