package br.com.argos.argosaisprint3.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class ProdutoDto {
    private Long id;

    @NotBlank(message = "O nome do produto não pode estar vazio.")
    @Size(max = 255, message = "O nome do produto não pode exceder 255 caracteres.")
    private String nome;

    @NotBlank(message = "A descrição não pode estar vazia.")
    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres.")
    private String descricao;

    @NotNull(message = "A quantidade do produto é obrigatória.")
    @Min(value = 0, message = "A quantidade do produto não pode ser negativa.")
    private Integer quantidade;
}
