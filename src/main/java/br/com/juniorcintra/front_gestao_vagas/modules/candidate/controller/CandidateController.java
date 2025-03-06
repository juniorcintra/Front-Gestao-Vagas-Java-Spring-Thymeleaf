package br.com.juniorcintra.front_gestao_vagas.modules.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.juniorcintra.front_gestao_vagas.modules.candidate.service.CandidateService;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;

  @GetMapping("/login")
  public String loginPage() {
    return "candidate/login";
  }

  @PostMapping("/login")
  public String login(RedirectAttributes redirectAttributes, String username, String password) {
    try {
      this.candidateService.login(username, password);

      return "candidate/profile";
    } catch (HttpClientErrorException e) {
      redirectAttributes.addFlashAttribute("error_message", "Usuário ou senha inválidos");
      return "redirect:/candidate/login";
    }
  }
}
