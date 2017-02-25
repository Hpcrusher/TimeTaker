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

define(['jquery', 'flipclock'], function ($) {

    FlipClock.Lang.Custom = { days:' ', hours:' ', minutes:' ', seconds:' ' };

    function getDefaultOptions() {
        return {
            clockFace: 'HourCounter',
            language: 'Custom',
            classes : {
                active: 'flip-clock-active',
                before: 'flip-clock-before',
                divider: 'flip-clock-divider',
                dot: 'flip-clock-dot',
                label: 'flip-clock-label',
                flip: 'flip',
                play: 'play',
                wrapper: 'flip-clock-small-wrapper'
            }
        }
    }

    function _ttTimer(startDate, options) {

        var pastDate = new Date(startDate);
        var currentDate = new Date(new Date().toISOString());
        // Calculate the difference in seconds between the future and current date
        var diff = (currentDate.getTime() - pastDate.getTime()) / 1000;

        var settings = $.extend(getDefaultOptions(), options);
        $(this).FlipClock(diff, settings);
    }

    $.fn.ttTimer = _ttTimer;

});