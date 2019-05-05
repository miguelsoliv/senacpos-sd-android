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

public class Order {
    private String Cliente, Descricao, Endereco, FormaPagamento, Hora, TelefoneCliente;
    private double Total;

    public Order() {
    }

    public Order(String Cliente, String Descricao, String Endereco, String FormaPagamento, String Hora,
                 String TelefoneCliente, double Total) {
        this.Cliente = Cliente;
        this.Descricao = Descricao;
        this.Endereco = Endereco;
        this.FormaPagamento = FormaPagamento;
        this.Hora = Hora;
        this.TelefoneCliente = TelefoneCliente;
        this.Total = Total;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String Endereco) {
        this.Endereco = Endereco;
    }

    public String getFormaPagamento() {
        return FormaPagamento;
    }

    public void setFormaPagamento(String FormaPagamento) {
        this.FormaPagamento = FormaPagamento;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public String getTelefoneCliente() {
        return TelefoneCliente;
    }

    public void setTelefoneCliente(String TelefoneCliente) {
        this.TelefoneCliente = TelefoneCliente;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }
}