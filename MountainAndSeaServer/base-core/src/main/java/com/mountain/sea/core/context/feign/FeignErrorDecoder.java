package com.mountain.sea.core.context.feign;

import com.mountain.sea.core.exception.RestApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * feign调用全局异常处理
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/26 15:22
 */
//暂时关闭该处理，当需要该处理时打开即可，由于与fallback存在先后顺序，先于fallback执行，此处先进行关闭
//todo 后续要考虑全局异常回滚与该类的关系，fallback无法自动触发全局回滚
//@Configuration
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception;
        try {
            String json = response.body().asReader(Charset.defaultCharset()).toString();
            exception = new RestApiException("501","feign请求异常");
//            Result result = JsonUtils.parse(json, Result.class);
//            if(!result.isSuccess()){
//                exception = new RestApiException("501","feign请求异常");
//            }else{
//                exception = new RestApiException("502","feign请求异常");
//            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestApiException("503","feign请求异常");
        }
        throw new RestApiException("503","feign请求异常");
//        return exception == null ? new RestApiException("504","feign请求异常"):exception;
    }
}
