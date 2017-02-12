/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import timetakers.exception.ForbiddenRuntimeException;
import timetakers.exception.ValidationRuntimeException;
import timetakers.util.TextKeyResolver;
import timetakers.web.model.ErrorDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @author David Liebl
 */

@ControllerAdvice
public class ExceptionHandlingController {

    private final TextKeyResolver textKeyResolver;

    @Autowired
    public ExceptionHandlingController(TextKeyResolver textKeyResolver) {
        this.textKeyResolver = textKeyResolver;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ValidationRuntimeException.class)
    @ResponseBody
    public ErrorDto validationFailed(ValidationRuntimeException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.elementId = exception.getElementId();
        errorDto.message = textKeyResolver.resolveTextKey(exception.getTextKey());
        return errorDto;
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ForbiddenRuntimeException.class)
    @ResponseBody
    public ErrorDto notAuthorized(ForbiddenRuntimeException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.message = textKeyResolver.resolveTextKey(exception.getTextKey());
        return errorDto;
    }



    @RequestMapping(value = "/error", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getErrorPageAsHtml(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        modelAndView.addObject("code", statusCode);
        modelAndView.addObject("description", HttpStatus.valueOf(statusCode).getReasonPhrase());
        return modelAndView;
    }

}
