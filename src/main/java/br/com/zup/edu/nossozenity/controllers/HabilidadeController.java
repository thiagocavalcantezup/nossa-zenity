package br.com.zup.edu.nossozenity.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HabilidadeController.BASE_URI)
public class HabilidadeController {

    public final static String BASE_URI = "/habilidades";

}
