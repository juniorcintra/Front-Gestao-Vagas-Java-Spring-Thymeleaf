package br.com.juniorcintra.front_gestao_vagas.modules.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.juniorcintra.front_gestao_vagas.modules.candidate.service.CandidateService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;

  @GetMapping("/login")
  public String loginPage() {
    return "candidate/login";
  }

  @PostMapping("/signIn")
  public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username,
      String password) {
    try {
      var token = this.candidateService.signIn(username, password);
      var grants = token.getRoles().stream()
          .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
          .toList();

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(null, null, grants);

      authentication.setDetails(token.getAccess_token());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      SecurityContext securityContext = SecurityContextHolder.getContext();

      session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
      session.setAttribute("token", token);

      return "redirect:/candidate/profile";
    } catch (HttpClientErrorException e) {
      redirectAttributes.addFlashAttribute("error_message", "Usuário ou senha inválidos");
      return "redirect:/candidate/login";
    }
  }


  @GetMapping("/profile")
  @PreAuthorize("hasRole('CANDIDATE')")
  public String profile(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    var user = this.candidateService.getProfile(authentication.getDetails().toString());

    model.addAttribute("user", user);

    return "candidate/profile";
  }
}
