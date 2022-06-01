package com.mountain.sea.response;

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

    public static Builder builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl implements Builder {
        private boolean success;
        private Object body;
        private Error error;

        private BuilderImpl() {
        }

        @Override
        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        @Override
        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        @Override
        public Builder error(ExceptionEnum exceptionInfoEnum) {
            this.error = new Error(exceptionInfoEnum);
            return this;
        }

        @Override
        public Builder error(String code, String message) {
            this.error = new Error(code, message);
            return this;
        }

        @Override
        public <T> Result<T> build() {
            return new Result(this.success, this.error, this.body);
        }
    }

    public interface Builder {
        Builder success(boolean var1);

        Builder body(Object var1);

        Builder error(ExceptionEnum var1);

        Builder error(String var1, String var2);

        <T> Result<T> build();
    }
}
