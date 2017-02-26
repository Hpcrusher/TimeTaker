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
 * @author david
 */

define(['jquery', 'connector'], function ($, CO) {

    var urlGetChildrenOfItem = '/item/{id}/children';
    var urlItemHasChildren = '/item/{id}/haschildren';
    var urlGetRootItems = '/item/root';
    var urlEditItem = '/item/{id}/edit';

    var $itemTreeModal = $('#itemTreeModal');
    var $treeElementTemplate = $('#treeElementTemplate');
    var $itemTreeContainer = $('#itemTreeContainer');

    var settings;
    var firstLoad = true;

    function getDefaultSettings() {
        return {
            showEditButton: false,
            currentItemId: null,
            baseUrl: ''
        };
    }

    function toggleSwitchIcon($button) {
        $button.toggleClass('glyphicon-plus');
        $button.toggleClass('glyphicon-minus');
    }

    function applyExtensionFunctionality($button, $li) {
        CO.get({
            url: settings.baseUrl + urlItemHasChildren.replace('{id}', $button.data('id')),
            success: function (data) {
                if (data.children) {
                    $button.click(function () {
                        CO.get({
                            url: settings.baseUrl + urlGetChildrenOfItem.replace('{id}', $button.data('id')),
                            success: function (items) {
                                var data = $($('#treeElementTemplate').render(items));
                                $li.append(applyItemTreeButtonFunctionality(data));
                                var children = $li.find('ul');
                                toggleSwitchIcon($button);
                                $button.unbind('click');
                                $button.click(function () {
                                    children.toggle();
                                    toggleSwitchIcon($button);
                                });
                            }
                        });
                    });
                } else {
                    $button.remove();
                }
            }
        });
    }

    function applyEditFunctionality($button) {
        $button.click(function () {
            window.location = settings.baseUrl + urlEditItem.replace('{id}', $button.data('id'));
        });
    }

    function applyItemTreeButtonFunctionality($renderedData, callback) {
        $renderedData.find('li').each(function () {
            var $li = $(this);

            applyExtensionFunctionality($li.find('.extend-btn'), $li);

            var $editBtn = $li.find('.edit-btn');
            if (settings.showEditButton) {
                applyEditFunctionality($editBtn);
            } else {
                $editBtn.toggle();
            }
            var span = $li.find('span');
            var itemId = span.data('id');
            if (!(itemId === settings.currentItemId)) {
                span.click(function () {
                    callback(itemId);
                });
            }
        });
        return $renderedData;
    }

    function loadRootElements(callback) {
        CO.get({
            url: settings.baseUrl + urlGetRootItems,
            success: function (data) {
                var $renderedData = $($treeElementTemplate.render(data));
                $renderedData = applyItemTreeButtonFunctionality($renderedData, callback);
                $itemTreeContainer.append($renderedData);
            }
        });
    }

    $.ttItemTree = function (callback, option) {

        if (option === 'hide') {
            $itemTreeModal.modal('hide');
            return;
        }


        if (firstLoad) {
            firstLoad = false;
            settings = $.extend(getDefaultSettings(), option);
            loadRootElements(callback);
        }

        $itemTreeModal.modal('show');
    }

});