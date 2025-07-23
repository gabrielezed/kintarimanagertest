// docs/source/Magazzino.html

package com.kintari.kintarimanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "magazzino")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Magazzino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingrediente_id", referencedColumnName = "id")
    private Ingrediente ingrediente;

    private double quantitaDisponibile;

    private double sogliaMinima;

}