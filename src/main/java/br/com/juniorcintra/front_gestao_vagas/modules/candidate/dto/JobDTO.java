package br.com.juniorcintra.front_gestao_vagas.modules.candidate.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {

  private UUID id;
  private String title;
  private String description;
  private String benefits;
  private String level;
  private UUID companyId;
  private Long createdAt;

}
