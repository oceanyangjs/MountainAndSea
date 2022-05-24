package com.mountain.sea.core.web;

import com.mountain.sea.core.annotation.ResponseBodyResult;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 11:17
 */
@ResponseBodyResult
@RequestMapping({"/"})
public class BaseController {
    public BaseController() {
    }
}
