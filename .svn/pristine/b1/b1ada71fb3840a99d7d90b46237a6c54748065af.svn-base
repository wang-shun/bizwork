package com.sogou.bizwork.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.constant.ErrorEnum;

/**
 * 异常handler处理器
 * @author dongzeguang
 *
 */
public class ExceptionHandler extends SimpleMappingExceptionResolver {
	private final Logger logger = Logger.getLogger(ExceptionHandler.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        logger.info(request.getRequestURI() + "--" + e.getMessage(), e);

        ModelAndView model = new ModelAndView(new MappingJacksonJsonView());
        Result result = new Result();

        if (e instanceof TypeMismatchException || e instanceof HttpMessageNotReadableException) {
            result.setError(ErrorEnum.PARAM_ERROR.getCode(), "参数类型转换错误");
        } else if (e instanceof BindException) {
            result.setError(ErrorEnum.PARAM_ERROR.getCode(), "参数绑定错误");
        } else {
            result.setError(ErrorEnum.SYSTEM_ERROR.getCode(), ErrorEnum.SYSTEM_ERROR.getMessage() + ":" + e.getMessage());
        }

        model.addObject("success", result.isSuccess());
        model.addObject("data", null);
        model.addObject("errorCode", result.getErrorCode());
        model.addObject("errorMsg", result.getErrorMsg());
        return model;
    }
}
