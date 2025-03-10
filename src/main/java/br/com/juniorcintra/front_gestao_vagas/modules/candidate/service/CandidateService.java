package br.com.juniorcintra.front_gestao_vagas.modules.candidate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.juniorcintra.front_gestao_vagas.modules.candidate.dto.CreateCandidateDTO;
import br.com.juniorcintra.front_gestao_vagas.modules.candidate.dto.JobDTO;
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

    var url = hostAPIGestaoVagas.concat("/candidate");

    try {
      var result = restTemplate.exchange(url, HttpMethod.GET, request, ProfileDTO.class);

      return result.getBody();
    } catch (Unauthorized e) {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

  public List<JobDTO> getJobs(String token, String filter) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

    var url = hostAPIGestaoVagas.concat("/company/jobs");

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromUriString(url).queryParam("filter", filter);

    ParameterizedTypeReference<List<JobDTO>> type =
        new ParameterizedTypeReference<List<JobDTO>>() {};

    try {
      var result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, type);

      return result.getBody();
    } catch (Unauthorized e) {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
  }

  public String applyJob(String token, UUID jobId) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(token);

    HttpEntity<UUID> request = new HttpEntity<>(jobId, headers);

    var url = hostAPIGestaoVagas.concat("/candidate/apply");

    var result = restTemplate.postForObject(url, request, String.class);
    return result;
  }

  public void createCandidate(CreateCandidateDTO createCandidateDTO) {

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<CreateCandidateDTO> request = new HttpEntity<>(createCandidateDTO, headers);

    var url = hostAPIGestaoVagas.concat("/candidate");

    restTemplate.postForObject(url, request, String.class);

  }

}
