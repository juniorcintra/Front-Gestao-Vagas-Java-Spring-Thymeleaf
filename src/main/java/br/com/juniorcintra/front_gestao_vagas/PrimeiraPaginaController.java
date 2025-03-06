package br.com.juniorcintra.front_gestao_vagas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PrimeiraPaginaController {

  @GetMapping("/")
  public String primeiraPaginaHTML(Model model) {
    model.addAttribute("message", "Hello World");

    return "primeiraPagina";
  }

  @GetMapping("/login")
  public String loginCandidate() {
    return "candidate/login";
  }

  @PostMapping("/cadastro")
  public String cadastroCandidate(Candidate candidate) {


    return "redirect:/login";
  }

  record Candidate(String name, String email, String username) {

  }
}
