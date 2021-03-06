/*
 * MIT License
 *
 * Copyright (c) 2019 Miguel Soares de Oliveira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.miguel.trabalhosd.model;

public class Product {
    private String Id, Nome, Descricao, Tipo;
    private double Preco;
    private int Quant;

    public Product() {
    }

    public Product(String Nome, String Descricao, String Tipo, double Preco) {
        this.Nome = Nome;
        this.Descricao = Descricao;
        this.Tipo = Tipo;
        this.Preco = Preco;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getNome() {
        return Nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public String getTipo() {
        return Tipo;
    }

    public double getPreco() {
        return Preco;
    }

    public int getQuant() {
        return Quant;
    }

    public void setQuant(int Quant) {
        this.Quant = Quant;
    }
}