package br.com.juniorcintra.front_gestao_vagas.modules.candidate.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestTemplate;
import br.com.juniorcintra.front_gestao_vagas.modules.candidate.dto.ProfileDTO;
import br.com.juniorcintra.front_gestao_vagas.modules.candidate.dto.Token;

@Service
public class CandidateService {

  @Value("${host.api.gestao.vagas}")
  private String hostAPIGestaoVagas;

  public Token signIn(String username, String password) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, String> data = new HashMap<>();
    data.put("username", username);
    data.put("password", password);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

    var url = hostAPIGestaoVagas.concat("/auth-candidate");

    var result = restTemplate.postForObject(url, request, Token.class);
    return result;
  }

  public ProfileDTO getProfile(String token) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

    try {
      var result = restTemplate.exchange("http://localhost:8080/candidate", HttpMethod.GET, request,
          ProfileDTO.class);

      return result.getBody();
    } catch (Unauthorized e) {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

}
