package com.mountain.sea.core.response;

import com.mountain.sea.core.exception.ExceptionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 11:11
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private boolean success;
    private T body;
    private Error error;

    public Result() {
    }

    public Result(boolean success, Error error, T body) {
        this.success = success;
        this.error = error;
        this.body = body;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Error getError() {
        return this.error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public static Result.Builder builder() {
        return new Result.BuilderImpl();
    }

    private static class BuilderImpl implements Result.Builder {
        private boolean success;
        private Object body;
        private Error error;

        private BuilderImpl() {
        }

        public Result.Builder body(Object body) {
            this.body = body;
            return this;
        }

        public Result.Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Result.Builder error(ExceptionEnum exceptionInfoEnum) {
            this.error = new Error(exceptionInfoEnum);
            return this;
        }

        public Result.Builder error(String code, String message) {
            this.error = new Error(code, message);
            return this;
        }

        public <T> Result<T> build() {
            return new Result(this.success, this.error, this.body);
        }
    }

    public interface Builder {
        Result.Builder success(boolean var1);

        Result.Builder body(Object var1);

        Result.Builder error(ExceptionEnum var1);

        Result.Builder error(String var1, String var2);

        <T> Result<T> build();
    }
}
