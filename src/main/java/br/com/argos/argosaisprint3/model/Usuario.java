package br.com.argos.argosaisprint3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "TB_ARGOS_USUARIO")  // Nome da tabela alterado
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "SEQ_ARGOS_USUARIO", allocationSize = 1)
    @Column(name = "COD_USUARIO")  // Nome da coluna no banco de dados
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String senha;

    // Relacionamento com perfis (roles)
    @Getter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_ARGOS_USUARIO_PERFIS", // Nome da tabela de junção alterado
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    private Set<Perfil> perfis;
}
