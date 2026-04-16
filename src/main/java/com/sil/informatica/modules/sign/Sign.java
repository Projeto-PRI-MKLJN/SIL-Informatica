package com.sil.informatica.modules.sign;

import jakarta.persistence.*;

@Entity
@Table(name = "signs")
public class Sign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String termo;
    private String descricao;
    private String categoria;
    private String videoUrl;

    public Sign() {
    }

    public Sign(String termo, String descricao, String categoria, String videoUrl) {
        this.termo = termo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.videoUrl = videoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
