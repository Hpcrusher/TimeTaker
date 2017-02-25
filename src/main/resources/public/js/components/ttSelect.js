/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

/**
 * @author David Liebl
 */

define(['jquery', 'select2'], function ($) {

    function getDefaultOptions() {
        return {
            allowClear: true,
            placeholder: '',
            ajax: {
                url: null,
                dataType: 'json',
                type: "GET",
                delay: 250,
                data: function (params) {
                    // Query paramters will be ?search=[term]&page=[page]
                    return {
                        search: params.term || '',
                        page: params.page || 0
                    };
                },
                processResults: function (data, params) {
                    return {
                        results: $.map(data.content, function (item) {
                            return {
                                id: item.oid,
                                text: item.title
                            };
                        }),
                        pagination: {
                            //TODO
                            more: params.page < data.page.totalPages
                        }
                    }
                },
                cache: true
            }
        }
    }

    function _ttSelect(options) {
        if (options === 'data') {
            return $(this).select2(options);
        }

        var settings = getDefaultOptions();
        if (options.url) {
            settings.ajax.url = options.url;
        } else {
            settings.ajax = null;
        }
        $(this).select2(settings);
    }

    $.fn.ttSelect = _ttSelect;

});