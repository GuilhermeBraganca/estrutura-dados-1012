package org.ada;


import org.ada.dto.Filme;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarregarDadosExternos {

    private static String CAMINHO_ARQUIVO = "src/main/resources/filmes.csv";

    public List<Filme> carregarFilmesCVS(){

        List<Filme> filmes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {

            br.readLine(); // Remover o cabeçalho

            String linha;
            while ((linha = br.readLine()) != null) {
                //String[] dados = linha.split(",");
                String[] partes = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                int id = Integer.parseInt(partes[0]);
                String title = partes[1].replace("\"", "");
                String overview = partes[2].replace("\"", "");
                String original_language = partes[3];
                String vote_count = partes[4];
                String vote_average = partes[5];

                Filme filme = new Filme(id,title,overview,original_language,vote_count,vote_average );
                filmes.add(filme);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filmes;

    }

}
