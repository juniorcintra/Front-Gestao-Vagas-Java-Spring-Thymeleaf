package br.com.juniorcintra.front_gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCandidateDTO {

  private String name;
  private String email;
  private String username;
  private String password;
  private String description;

}
