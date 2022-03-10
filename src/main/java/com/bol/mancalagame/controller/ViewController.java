package com.bol.mancalagame.controller;

import com.bol.mancalagame.constant.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for view
 */
@Controller
public class ViewController {

    @GetMapping("/")
    public String main(Model model) {
        return Constants.HOME_TEMPLATE;
    }
}
