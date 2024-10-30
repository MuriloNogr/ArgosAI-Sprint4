package br.com.argos.argosaisprint3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TB_ARGOS_PERFIL")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_seq")
    @SequenceGenerator(name = "perfil_seq", sequenceName = "SEQ_ARGOS_PERFIL", allocationSize = 1)
    @Column(name = "COD_PERFIL")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
}
