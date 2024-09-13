package br.com.argos.argosaisprint3.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class ClienteDto extends RepresentationModel<ClienteDto> {
    private Long id;

    @NotBlank(message = "O nome do cliente não pode estar vazio.")
    @Size(max = 255, message = "O nome do cliente não pode exceder 255 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail do cliente não pode estar vazio.")
    @Size(max = 255, message = "O e-mail do cliente não pode exceder 255 caracteres.")
    private String email;

    @NotBlank(message = "O CPF do cliente é obrigatório.")
    @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres.")
    private String cpf;

    @NotBlank(message = "O celular do cliente é obrigatório.")
    @Size(min = 10, max = 15, message = "O celular deve ter entre 10 e 15 caracteres.")
    private String celular;

    @NotNull(message = "A idade do cliente é obrigatória.")
    @Min(value = 0, message = "A idade do cliente não pode ser negativa.")
    private Integer idade;
}
