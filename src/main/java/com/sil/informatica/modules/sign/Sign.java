package com.sil.informatica.modules.sign;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

/// Representa um termo técnico (sinal) no glossário de informática.
///
/// Esta entidade armazena o vocabulário, descrições lógicas e referências visuais (vídeos)
/// para termos utilizados na área de TI.
@Entity
@Table(name = "signs")
public class Sign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O termo é obrigatório")
    @Column(nullable = false)
    private String term;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "A categoria é obrigatória")
    @Column(nullable = false)
    private String category;

    private String videoUrl;

    /// Construtor padrão exigido pelo JPA.
    public Sign() {
    }

    /// Construtor completo para criação de novos sinais.
    ///
    /// @param term O nome do termo técnico.
    /// @param description Explicação detalhada do termo.
    /// @param category Categoria à qual o termo pertence (ex: Programação).
    /// @param videoUrl Link para o vídeo demonstrativo do sinal.
    public Sign(String term, String description, String category, String videoUrl) {
        this.term = term;
        this.description = description;
        this.category = category;
        this.videoUrl = videoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Sign sign = (Sign) o;
        return Objects.equals(id, sign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}