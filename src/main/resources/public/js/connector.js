/*
 * Copyright (c) 2017 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

define('connector',['jquery'], function($){

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    function ajaxRequest(method, options) {
        $.ajax({
            url: options.url,
            method: method,
            data: JSON.stringify(options.data),
            success: options.success,
            error: options.error,
            contentType: 'application/json; charset=utf-8'
        });
    }

    var _put = function (options) {
        ajaxRequest('PUT', options);
    };

    var _delete = function (options) {
        ajaxRequest('DELETE', options);
    };

    var _get = function (options) {
        ajaxRequest('GET', options);
    };

    var _post = function (options) {
        ajaxRequest('POST', options);
    };

    return {
        put: _put,
        delete: _delete,
        get: _get,
        post: _post
    };
});