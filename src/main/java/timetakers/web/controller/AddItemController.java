package timetakers.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import timetakers.repository.ItemRepository;
import timetakers.web.assembler.ItemAssembler;

/**
 * @author Sebastian Pettirsch
 */


@Controller
@Transactional
@RequestMapping(value = "/additem")

public class AddItemController {

    private ItemRepository itemRepository;
    private ItemAssembler itemAssembler;

    @Autowired
    public AddItemController(ItemRepository itemRepository, ItemAssembler itemAssembler) {
        this.itemRepository = itemRepository;
        this.itemAssembler = itemAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHomeAsHtml() {
        ModelAndView modelAndView = new ModelAndView("additem");
        modelAndView.addObject("items", itemAssembler.toResources(itemRepository.findAll()));
        return modelAndView;
    }
}
