package com.imd.supermercado.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.imd.supermercado.services.EmpresaService;
import com.imd.supermercado.services.ProdutoService;

@Controller
@RequestMapping("/pagina-inicial")
public class HomeController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ModelAndView home() {

        ModelAndView mv = new ModelAndView("pagina-inicial");

        mv.addObject("lojas", empresaService.listarEmpresas());
        mv.addObject("produtos", produtoService.buscarProdutos());

        return mv;
    }

    @GetMapping("/produtos-todos")
    public ModelAndView listProducts(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "precoMin", required = false) String precoMinStr,
            @RequestParam(name = "precoMax", required = false) String precoMaxStr) {

        Double precoMin = (precoMinStr == null || precoMinStr.isEmpty()) ? 0.0 : Double.valueOf(precoMinStr);
        Double precoMax = (precoMaxStr == null || precoMaxStr.isEmpty()) ? Double.MAX_VALUE
                : Double.valueOf(precoMaxStr);

        ModelAndView mv = new ModelAndView("/produtos-todos");
        mv.addObject("lojas", empresaService.listarEmpresas());
        mv.addObject("produtos", produtoService.buscarProdutos(nome, precoMin, precoMax));
        System.out.println(nome);
        return mv;
    }
}