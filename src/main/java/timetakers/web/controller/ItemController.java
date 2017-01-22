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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import timetakers.model.Item;
import timetakers.repository.ItemRepository;
import timetakers.web.assembler.ItemAssembler;
import timetakers.web.model.ItemDto;

import java.awt.*;
import java.util.List;
import java.util.UUID;


/**
 * @author Martin Geßenich
 */

@Controller
@Transactional
@RequestMapping(value = "/item")
public class ItemController {

    private ItemRepository itemRepository;
    private ItemAssembler itemAssembler;

    @Autowired
    public ItemController(ItemRepository itemRepository, ItemAssembler itemAssembler) {
        this.itemRepository = itemRepository;
        this.itemAssembler = itemAssembler;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public void postNewItem( @RequestBody ItemDto itemDto ) {
        Item father = itemRepository.getOne(itemDto.father);
        Item item = Item.builder().withTitle(itemDto.title)
                .withFather(father)
                .withColor(Color.decode(itemDto.color)).createItem();

        itemRepository.save(item);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDto> getItemAsJson() {
        return itemAssembler.toResources(itemRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemDto getItemWithIdAsJson(@PathVariable UUID id) {
        return itemAssembler.toResource(itemRepository.findOne(id));
    }

}
