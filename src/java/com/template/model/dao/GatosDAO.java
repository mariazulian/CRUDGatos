package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.GatosDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class GatosDAO {
    private static final Logger logger = Logger.getLogger(GatosDAO.class.getName());

    public ArrayList<GatosDTO> selecionarGatos() {
        String sql = "SELECT * FROM gatos";
        ArrayList<GatosDTO> lista = new ArrayList<>();

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GatosDTO gato = new GatosDTO();
                gato.setId(rs.getInt("id"));
                gato.setIdade(rs.getInt("idade"));
                gato.setRaca(rs.getString("raca"));
                gato.setPelagem(rs.getString("pelagem"));
                gato.setSexo(rs.getString("sexo"));

                lista.add(gato);
            }
        } catch (SQLException e) {
            logger.severe("Erro ao selecionar gatos: " + e.getMessage());
        }

        return lista;
    }

    public void cadastrarGatos(GatosDTO gatos) {
        String sql = "INSERT INTO gatos (idade, raca, pelagem, sexo) VALUES (?, ?, ?, ?)";
        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, gatos.getIdade());
            ps.setString(2, gatos.getRaca());
            ps.setString(3, gatos.getPelagem());
            ps.setString(4, gatos.getSexo());
            ps.execute();
        } catch (SQLException e) {
            logger.severe("Erro ao cadastrar gato: " + e.getMessage());
        }
    }

    public void atualizarGatos(GatosDTO gatos) {
        String sql = "UPDATE gatos SET idade=?, raca=?, pelagem=?, sexo=? WHERE id=?";
        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, gatos.getIdade());
            ps.setString(2, gatos.getRaca());
            ps.setString(3, gatos.getPelagem());
            ps.setString(4, gatos.getSexo());
            ps.setInt(5, gatos.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar gato: " + e.getMessage());
        }
    }

    public void deletarGatos(int id) {
        String sql = "DELETE FROM gatos WHERE id=?";
        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Erro ao deletar gato: " + e.getMessage());
        }
    }
}
